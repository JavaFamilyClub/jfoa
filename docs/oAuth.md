# oAuth 2.0 Login

## 1. page link
> 页面链接用于请求到产品服务器(`jfoa`)获取 `rest` 服务器的秘钥(e.g. client_id/client_secrets)
> 页面链接请求地址规范为:

```http request
/public/oauth/auth
```

> for example:

```http request
https://javafamily.club/public/oauth/auth
```

> 页面链接需要自己请求转发到 `rest` 服务器的认证接口.

## 2. `rest` 服务器认证回调
> 通常 `oAuth` 都会需要一个 `callback` 用于返回 `rest` 服务器返给我们服务器的 `code` 等信息.
> 服务器认证回调地址规范为:

```http request
/public/oauth/callback
```

> for example:

```http request
https://javafamily.club/public/oauth/callback
```

> 在回调中需要做如下工作:
> * 接受 `rest` 服务器返回的 `code` 和 `state`
> * 认证 `rest` 服务器返回的 `state`. (一定时间内有效, 比如 GitHub, 10 分钟内有效)
> * 借助 `HTTPClient` 等工具发起 `callback` 请求得到 `access_token`
> * 向 `rest` 服务器发起资源请求, 获取用户名, 邮箱等用户身份信息
> * 根据 `rest` 服务器查询到的用户身份信息查询产品服务器, 判断用户身份信息是否已经插入产品服务器, 如果没有, 先插入用户信息(相当于注册), 再执行登录, 否则执行用户登录
