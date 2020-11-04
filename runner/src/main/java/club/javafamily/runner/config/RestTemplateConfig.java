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

import club.javafamily.runner.properties.HttpClientProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.*;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.HTTP;
import org.apache.http.ssl.SSLContextBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.*;
import java.nio.charset.Charset;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;

@Configuration
@ConditionalOnClass(value = {RestTemplate.class, CloseableHttpClient.class})
@EnableConfigurationProperties(HttpClientProperties.class)
public class RestTemplateConfig {

   private final HttpClientProperties httpClientProperties;
   private final ObjectMapper objectMapper;

   @Autowired
   public RestTemplateConfig(HttpClientProperties httpClientProperties,
                             ObjectMapper objectMapper)
   {
      this.httpClientProperties = httpClientProperties;
      this.objectMapper = objectMapper;
   }

   @Bean
   public HttpClient httpClient() {
      HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();

      try {
         //设置信任ssl访问
//         SSLContext sslContext = new SSLContextBuilder()
//            .loadTrustMaterial(null, (arg0, arg1) -> true).build();

         SSLContext sslContext =SSLContext.getInstance("SSL");

         TrustManager[] trustAllCerts =new TrustManager[] {
            new X509TrustManager() {
               @Override
               public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
               }

               @Override
               public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
               }

               @Override
               public X509Certificate[] getAcceptedIssuers() {
                  return new X509Certificate[0];
               }
            }
         };

         sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

         httpClientBuilder.setSSLContext(sslContext);
         HostnameVerifier hostnameVerifier = NoopHostnameVerifier.INSTANCE;
         SSLConnectionSocketFactory sslConnectionSocketFactory
            = new SSLConnectionSocketFactory(sslContext, hostnameVerifier);
         Registry<ConnectionSocketFactory> socketFactoryRegistry
            = RegistryBuilder.<ConnectionSocketFactory>create()
            // 注册http和https请求
            .register("http", PlainConnectionSocketFactory.getSocketFactory())
            .register("https", sslConnectionSocketFactory).build();

         //使用Httpclient连接池的方式配置(推荐)，同时支持netty，okHttp以及其他http框架
         PoolingHttpClientConnectionManager poolingHttpClientConnectionManager
            = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
         // 最大连接数
         poolingHttpClientConnectionManager.setMaxTotal(httpClientProperties.getMaxTotalConnect());
         // 同路由并发数
         poolingHttpClientConnectionManager.setDefaultMaxPerRoute(
            httpClientProperties.getMaxConnectPerRoute());
         //配置连接池
         httpClientBuilder.setConnectionManager(poolingHttpClientConnectionManager);
         // 重试次数
         httpClientBuilder.setRetryHandler(new DefaultHttpRequestRetryHandler(
            httpClientProperties.getRetryTimes(), true));

         //设置默认请求头
         List<Header> headers = getDefaultHeaders();
         httpClientBuilder.setDefaultHeaders(headers);
         //设置长连接保持策略
         httpClientBuilder.setKeepAliveStrategy(connectionKeepAliveStrategy());

         return httpClientBuilder.build();
      }
      catch(Exception e) {
         LOGGER.error("Init HTTP Client Error.", e);
      }

      return null;
   }

   @Bean
   public ClientHttpRequestFactory clientHttpRequestFactory() {
      if (httpClientProperties.getMaxTotalConnect() <= 0) {
         throw new IllegalArgumentException("invalid maxTotalConnection: "
            + httpClientProperties.getMaxTotalConnect());
      }
      if (httpClientProperties.getMaxConnectPerRoute() <= 0) {
         throw new IllegalArgumentException("invalid maxConnectionPerRoute: "
            + httpClientProperties.getMaxConnectPerRoute());
      }

      HttpComponentsClientHttpRequestFactory clientHttpRequestFactory
         = new HttpComponentsClientHttpRequestFactory(httpClient());
      // 连接超时
      clientHttpRequestFactory.setConnectTimeout(httpClientProperties.getConnectTimeout());
      // 数据读取超时时间，即SocketTimeout
      clientHttpRequestFactory.setReadTimeout(httpClientProperties.getReadTimeout());
      // 从连接池获取请求连接的超时时间，不宜过长，必须设置，比如连接不够用时，时间过长将是灾难性的
      clientHttpRequestFactory.setConnectionRequestTimeout(
         httpClientProperties.getConnectionRequestTimout());

      return clientHttpRequestFactory;
   }

   @Bean
   public RestTemplate mainRestTemplate(ClientHttpRequestFactory factory){
      return createRestTemplate(factory);
   }

   /**
    * 配置长连接保持策略
    */
   public ConnectionKeepAliveStrategy connectionKeepAliveStrategy(){
      return (response, context) -> {
         // 'keep-alive' header
         HeaderElementIterator it = new BasicHeaderElementIterator(
            response.headerIterator(HTTP.CONN_KEEP_ALIVE));
         try {
            while (it.hasNext()) {
               HeaderElement he = it.nextElement();
               LOGGER.info("HeaderElement:{}", objectMapper.writeValueAsString(he));

               String param = he.getName();
               String value = he.getValue();
               if (value != null && "timeout".equalsIgnoreCase(param)) {
                  return Long.parseLong(value) * 1000;
               }
            }
         }
         catch(Exception e) {
            LOGGER.error("解析长连接过期时间异常", e);
         }

         HttpHost target = (HttpHost) context.getAttribute(
            HttpClientContext.HTTP_TARGET_HOST);
         //如果请求目标地址,单独配置了长连接保持时间,使用该配置
         Optional<Map.Entry<String, Integer>> any =
            Optional.ofNullable(httpClientProperties.getKeepAliveTargetHost())
               .orElseGet(HashMap::new)
               .entrySet()
               .stream()
               .filter(e -> e.getKey().equalsIgnoreCase(target.getHostName())).findAny();

         //否则使用默认长连接保持时间
         return any.map(en -> en.getValue() * 1000L)
            .orElse(httpClientProperties.getKeepAliveTime() * 1000L);
      };
   }

   /**
    * 设置请求头
    */
   private List<Header> getDefaultHeaders() {
      List<Header> headers = new ArrayList<>();
      headers.add(new BasicHeader("User-Agent",
         "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.75 Safari/537.36"));
      headers.add(new BasicHeader("Accept-Encoding", "gzip,deflate"));
      headers.add(new BasicHeader("Accept-Language", "zh-CN,zh;q=0.9"));
      headers.add(new BasicHeader("Connection", "keep-alive"));
//      headers.add(new BasicHeader("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE));
//      headers.add(new BasicHeader("Accept", "application/json, text/plain, */*"));

      return headers;
   }

   private RestTemplate createRestTemplate(ClientHttpRequestFactory factory) {
      RestTemplate restTemplate = new RestTemplate(factory);

      //我们采用RestTemplate内部的MessageConverter
      //重新设置StringHttpMessageConverter字符集，解决中文乱码问题
      modifyDefaultCharset(restTemplate);

      //设置错误处理器
      restTemplate.setErrorHandler(new DefaultResponseErrorHandler());

      return restTemplate;
   }

   /**
    * 修改默认的字符集类型为utf-8
    *
    * @param restTemplate
    */
   private void modifyDefaultCharset(RestTemplate restTemplate) {
      List<HttpMessageConverter<?>> converterList = restTemplate.getMessageConverters();
      HttpMessageConverter<?> converterTarget = null;
      for (HttpMessageConverter<?> item : converterList) {
         if (StringHttpMessageConverter.class == item.getClass()) {
            converterTarget = item;
            break;
         }
      }
      if (null != converterTarget) {
         converterList.remove(converterTarget);
      }

      Charset defaultCharset = Charset.forName(httpClientProperties.getCharset());
      converterList.add(1, new StringHttpMessageConverter(defaultCharset));
   }

   private static final Logger LOGGER = LoggerFactory.getLogger(RestTemplateConfig.class);
}
