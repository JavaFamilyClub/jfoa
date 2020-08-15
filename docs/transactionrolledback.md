# UnexpectedRollbackException: Transaction rolled back because it has been marked as rollback-only

## 1. 问题描述与重现

> `jfoa` 提供了 `Audit` 的功能, 目标方法(比如添加用户)存在事务管理,
> 在 Audit 中需要去查询用户(事务管理), 最后在目标方法执行后执行 Audit 记录的插入(事务管理).
> 即添加用户(事务)中查询用户(事务)就会抛出异常:

``` java
org.springframework.transaction.UnexpectedRollbackException: Transaction rolled back because it has been marked as rollback-only
```

> 字面意思是: `出现了不可预知的回滚异常，因为事务已经被标志位只能回滚，所以事务回滚了.`
>
> 下面来重现和描述一下这个问题.

``` java
  @Around("audit()")
  public Object recordLog(ProceedingJoinPoint pjp) throws Throwable {
    Log log = null;
    Customer principal = null;

    try {
      // note1: 这里获取当前登录的用户, 注意这个方法可以抛出异常
      // 1) getCurrentCustomer 在事务管理中
      // 2) catch 了方法调用并没有继续抛出异常
      principal = customerService.getCurrentCustomer();
    } catch (Exception ignore) {
      LOGGER.debug("Get principal error!", ignore);
    }

    // ... 省略一些中间代码

    Object result;

    try {
      // note3: 具体的目标方法
      result = pjp.proceed();
    }
    catch(Throwable throwable) {
      if(log != null) {
        log.setMessage("Execute Failed: " + throwable.getMessage());
      }

      throw throwable;
    }
    finally {
      // ...
    }

    return result;
  }
```

``` java
   // note2: 目标方法有事务管理
   @Audit(ResourceEnum.Customer)
   @Transactional
   @Override
   public Integer insertCustomer(@AuditObject("getName()") Customer user) {
      if(isValid(user)) {
         user.setPassword(
            SecurityUtil.generatorPassword(user.getAccount(), user.getPassword()));

         return customerDao.insert(user);
      }

      return null;
   }
```

> 注意上方的 `note1` 和 `note2`, 在这种情况下就会出现本文的异常.

## 2. 原理剖析

> 因为 spring 的 `@Transactional` 注解默认的事务传播策略为 `Propagation.REQUIRED`,
> PROPAGATION_REQUIRED的意思是，当前有事务，则使用当前事务，当前无事务则创建事务。因此, 当
> 目标方法被调用时, 由于目标方法存在事务, 当目标方法被调用时会开启事务, 
> 当执行到 `note1` 时, `getCurrentCustomer` 方法也
> 存在事务, 根据事务传播策略, spring 就会使用目标方法的事务,
> 但是 `getCurrentCustomer` 有 try-catch 处理, 并且 `catch` 没有继续抛出事务,
> 因此当 `getCurrentCustomer` 抛出异常时, spring 要执行 rollback 操作, 但是 catch
> 没有继续抛出异常, 还会继续调用到 `note3` 出继续执行, 当目标方法被调用完成后
> 由于是正常调用完毕, 因此又需要执行 commit, 所以就会引发该异常.
>

### 2.0 事务传播策略简介
> spring 定义了如下传播策略:

``` java
public enum Propagation {

	/**
	 * 当前有事务，则使用当前事务，当前无事务则创建事务
	 */
	REQUIRED(TransactionDefinition.PROPAGATION_REQUIRED),

	/**
	 * 支持当前事务，如果不存在，以非事务方式执行。
     *  注:对于事务同步的事务管理器，与根本没有事务略有不同，
     *  因为它定义了同步将应用的事务范围。
     *  结果，相同的资源(JDBC连接、Hibernate会话等)
     *  将为整个指定范围共享。注意，这取决于
     *  事务管理器的实际同步配置。
	 */
	SUPPORTS(TransactionDefinition.PROPAGATION_SUPPORTS),

	/**
	 * 如果当前与事务则使用当前事务, 如果当前没事务则抛出异常.
	 */
	MANDATORY(TransactionDefinition.PROPAGATION_MANDATORY),

	/**
     * 无论当前是否有事务都创建一个新的事务.
     * 如果当前有事务则挂起当前事务. 
	 */
	REQUIRES_NEW(TransactionDefinition.PROPAGATION_REQUIRES_NEW),

	/**
     * 以非事务方式运行, 如果当前有事务, 则挂起当前事务
	 */
	NOT_SUPPORTED(TransactionDefinition.PROPAGATION_NOT_SUPPORTED),

	/**
	 * 以非事务方式运行, 如果当前有事务, 则抛出异常
	 */
	NEVER(TransactionDefinition.PROPAGATION_NEVER),

	/**
	 * 如果当前事务存在，则在嵌套事务中执行，否则行为类似{@code REQUIRED}.
     *   注意: 嵌套事务的实际创建只适用于特定的事务管理器。
     *   这只适用于JDBC DataSourceTransactionManager。
     *   一些JTA提供程序可能也支持嵌套事务。
	 */
	NESTED(TransactionDefinition.PROPAGATION_NESTED);
```

### 2.1 默认传播策略
> spring 的 `@Transactional` 注解默认的事务传播策略为 `Propagation.REQUIRED` 

``` java
public @interface Transactional {

    // ...
    
	/**
	 * The transaction propagation type.
	 * <p>Defaults to {@link Propagation#REQUIRED}.
	 * @see org.springframework.transaction.interceptor.TransactionAttribute#getPropagationBehavior()
	 */
	Propagation propagation() default Propagation.REQUIRED;
```

### 2.2 问题总结
> 当 methodA 调用 methodB, 并且两个方都有事务(事务嵌套), 
> 且传播策略都为 `Propagation.REQUIRED` 时, 就会导致两个方法
> 共用一个事务，methodB将事务标志为回滚，methodA中commit这个事务，
> 然后就会出现事务已经被标志回滚（methodB标志的）的异常信息.

## 3. 解决方法

> 知道了问题出现的原因, 自然就知道了解决办法, 但是不同的业务场景解决方式可能不同,
> 大家需要结合自己的业务场景选择合适的解决方案

### 3.1 修改 try-catch

> 对内部方法 methodB 不要进行 try-catch 或者 catch 继续抛出异常.

### 3.2 修改事务传播策略
> * 如果修改外部事务 methodA 的事务传播策略, 则 methodA 只能取消事务(一般不可用).
> * 修改内部 methodB 的事务传播策略, 一般可改为 `REQUIRES_NEW`, `NOT_SUPPORTED`, `NESTED`. 
>       `NOT_SUPPORTED` 都把内部事务转换为非事务运行.
>       `REQUIRES_NEW` 会创建一个新事务运行.
>       `NESTED` 则是在嵌套事务运行.

> 比如帅帅我这里的业务场景, 首先是需要事务管理, 因此我这里可以使用 `REQUIRES_NEW`, `NESTED`, 
> 其次, audit 记录以及, `getCurrentCustomer` 无论何时都不应该影响核心业务逻辑的运行,因此, 
> 最终我选择修改事务传播策略为 `REQUIRES_NEW` 来解决这个问题. 如果您的嵌套事务和主事务有关联关系,
> 那么您就应该选择 `NESTED`.

**最后, 还需要注意一点: 同一个类中，事务嵌套以最外层的方法为准，嵌套的事务失效；不同类中嵌套的事务才会生效；**
