# oAuth 2.0 Login

## 1. page link
> 页面链接用于请求到 Server 获取 `rest` 服务器的秘钥(e.g. client_id/client_secrets)
> 页面链接请求地址规范为:

```http request
/public/oauth/${restName}/auth
```

> for example:

```http request
https://javafamily.club/public/oauth/github/auth
```

> 页面链接需要自己转发到 `rest` 服务器的认证接口

## 2. `rest` 服务器认证与授权
