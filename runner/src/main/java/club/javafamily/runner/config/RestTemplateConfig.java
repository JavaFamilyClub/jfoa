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

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

   @Bean
   public RestTemplate mainRestTemplate(){
      SimpleClientHttpRequestFactory httpRequestFactory
         = new SimpleClientHttpRequestFactory();
//      httpRequestFactory.setConnectionRequestTimeout(60 * 1000);
      httpRequestFactory.setConnectTimeout(60 * 1000);
      httpRequestFactory.setReadTimeout(60 * 1000);

      return new RestTemplate(httpRequestFactory);
   }
}
