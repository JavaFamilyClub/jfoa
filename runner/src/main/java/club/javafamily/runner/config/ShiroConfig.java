/*
 * Copyright (c) 2020, JavaFamily Technology Corp, All Rights Reserved.
 *
 * The software and information contained herein are copyrighted and
 * proprietary to JavaFamily Technology Corp. This software is furnished
 * pursuant to a written license agreement and may be used, copied,
 * transmitted, and stored only in accordance with the terms of such
 * license and with the inclusion of the above copyright notice. Please
 * refer to the file "COPYRIGHT" for further copyright and licensing
 * information. This software and information or any other copies
 * thereof may not be provided or otherwise made available to any other
 * person.
 */

package club.javafamily.runner.config;

import club.javafamily.runner.filter.RequestFilter;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.permission.WildcardPermissionResolver;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.*;

import javax.servlet.Filter;
import java.util.Map;

import static club.javafamily.runner.util.SecurityUtil.API_VERSION;
import static club.javafamily.runner.util.SecurityUtil.CLIENT_API_VERSION;

/**
 * Shiro filter should execute before urlRewriteFilter.
 * implement by {{@link #shiroFilter}} using {{@link OrderedShiroFilterFactoryBean}}.
 *
 * Filter Order:
 *    ShiroFilter: 1
 *    urlRewriteFilter: 2
 *
 * {{@link UrlRewriteFilterConfig#urlRewriteFilter()}}
 */
@Configuration
public class ShiroConfig {

   @Bean("lifecycleBeanPostProcessor")
   public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
      return new LifecycleBeanPostProcessor(0);
   }

   @Bean
   @DependsOn("lifecycleBeanPostProcessor")
   @ConditionalOnMissingBean
   public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
      DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator
         = new DefaultAdvisorAutoProxyCreator();
      advisorAutoProxyCreator.setProxyTargetClass(true);

      return advisorAutoProxyCreator;
   }

   @Bean("shiroCacheManager")
   @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
   public CacheManager cacheManager(net.sf.ehcache.CacheManager ehCacheCacheManager) {
      EhCacheManager ehCacheManager = new EhCacheManager();
      ehCacheManager.setCacheManager(ehCacheCacheManager);

      return ehCacheManager;
   }

   @Bean("securityManager")
   public SecurityManager securityManager(AuthorizingRealm realm,
                                          CacheManager cacheManager)
   {
      DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager(realm);
      securityManager.setCacheManager(cacheManager);

      return securityManager;
   }

   @Bean
   public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
      ShiroFilterFactoryBean shiroFilterFactoryBean
         = new OrderedShiroFilterFactoryBean();
      shiroFilterFactoryBean.setSecurityManager(securityManager);

      // 设置默认登录的 URL，身份认证失败会访问该 URL
      shiroFilterFactoryBean.setLoginUrl("/login");
      // 设置成功之后要跳转的链接
      shiroFilterFactoryBean.setSuccessUrl("/success");
      // 设置未授权界面，权限认证失败会访问该 URL
      shiroFilterFactoryBean.setUnauthorizedUrl("/unauthorized");

      // LinkedHashMap 是有序的，进行顺序拦截器配置
      Map<String,String> filterChainMap
         = shiroFilterFactoryBean.getFilterChainDefinitionMap();

      // 配置可以匿名访问的地址，可以根据实际情况自己添加，放行一些静态资源等，anon 表示放行
      filterChainMap.put("/css/**", "anon");
      filterChainMap.put("/", "anon");
      filterChainMap.put("/index.html", "anon");
      filterChainMap.put("/**/*.ico", "anon");
      filterChainMap.put("/imgs/**", "anon");
      filterChainMap.put("/js/**", "anon");
      filterChainMap.put("/assets/**", "anon");
      filterChainMap.put("/webjars/**", "anon");

      // swagger
      filterChainMap.put("swagger-ui.html", "anon");
      filterChainMap.put("/swagger-ui.html", "anon");
      filterChainMap.put("/swagger-resources/**", "anon");
      filterChainMap.put("/v2/api-docs/**", "anon");
      filterChainMap.put("/webjars/springfox-swagger-ui/**", "anon");

      // websocket
      filterChainMap.put(WebSocketConfig.JF_SOCKET_CHANNEL + "/**", "anon");

      // web control
      // em
      filterChainMap.put("/app/em/**", "authc");
      // write article and mail author
      // TODO should control by permission
      filterChainMap.put("/app/portal/mailAuthor", "authc");
      filterChainMap.put("/app/portal/blog/article-editor/**", "authc");

      // portal and others.
      filterChainMap.put("/app/portal/**", "anon");
      filterChainMap.put("/app/**", "anon");

      // login
      filterChainMap.put("/login", "anon");
      filterChainMap.put(API_VERSION + "/login", "anon");
      filterChainMap.put(CLIENT_API_VERSION + "/login", "anon");

      // sign up
      filterChainMap.put("/signup", "anon");
      filterChainMap.put(API_VERSION + "/signup", "anon");
      filterChainMap.put(API_VERSION + "/customer/verify", "anon");

      // public api
      filterChainMap.put("/public/**", "anon");
      filterChainMap.put(API_VERSION + "/public/**", "anon");
      filterChainMap.put(CLIENT_API_VERSION + "/public/**", "anon");
      filterChainMap.put(API_VERSION + "/api/**", "anon");
      filterChainMap.put(CLIENT_API_VERSION + "/api/**", "anon");

      // error page
      filterChainMap.put("/error/**", "anon");

      // oauth
      filterChainMap.put("/wb_e26919bbb00c4f57.txt", "anon");

      // logout filter
      filterChainMap.put("/logout", "logout");

      // others
      filterChainMap.put("/**", "authc");

      shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainMap);

      // 添加 Filter
      Map<String, Filter> filters = shiroFilterFactoryBean.getFilters(); // LinkedHashMap
      filters.put("requestFilter", requestFilter());

      return shiroFilterFactoryBean;
   }

   @Bean
   public RequestFilter requestFilter() {
      return new RequestFilter();
   }

   /**
    * 用于映射匹配权限注解.
    */
   @Bean
   public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(
      SecurityManager securityManager)
   {
      AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor
         = new AuthorizationAttributeSourceAdvisor();
      authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);

      return authorizationAttributeSourceAdvisor;
   }

   /**
    * 密码加密.
    */
   @Bean
   public CredentialsMatcher credentialsMatcher() {
      HashedCredentialsMatcher credentialsMatcher
         = new HashedCredentialsMatcher("MD5");
      credentialsMatcher.setHashIterations(1024);

      return credentialsMatcher;
   }

   /**
    * 用于匹配 @RequiresPermissions 注解中的通配符. * 代表任意.
    * @return PermissionResolver
    */
   @Bean
   public WildcardPermissionResolver wildcardPermissionResolver() {
      return new MainWildcardPermissionResolver();
   }
}
