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

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.*;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.tuckey.web.filters.urlrewrite.UrlRewriteFilter;

@Configuration
public class UrlRewriteFilterConfig {

   public static final String URL_REWRITE = "classpath:urlrewrite.xml";

   /**
    * reset filter order, must execute after shiro filter.
    * @see ShiroConfig reset filter order
    */
   @Bean()
   @Order(2)
   public FilterRegistrationBean<UrlRewriteFilter> urlRewriteFilter() {
      MainUrlRewriteFilter mainUrlRewriteFilter = new MainUrlRewriteFilter();

      FilterRegistrationBean<UrlRewriteFilter> filterRegistration
         = new FilterRegistrationBean<>(mainUrlRewriteFilter);

      filterRegistration.setOrder(Ordered.LOWEST_PRECEDENCE);

      return filterRegistration;
   }

}
