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

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;

/**
 * Shiro filter execute after urlRewriteFilter default, This class
 * will reset order of shiroFilter to before urlRewriteFilter.
 *
 * {{@link MainUrlRewriteFilter}}
 * {{@link UrlRewriteFilterConfig#urlRewriteFilter()}}
 */
public class OrderedShiroFilterFactoryBean extends ShiroFilterFactoryBean {

   @Override
   public Object getObject() throws Exception {
      AbstractShiroFilter object = (AbstractShiroFilter) super.getObject();

      if(object != null) {
         FilterRegistrationBean<AbstractShiroFilter> filter
            = new FilterRegistrationBean<>(object);

         // reset order of shiroFilter to before urlRewriteFilter.
         filter.setOrder(1);

         return filter;
      }

      return null;
   }

   @Override
   public Class getObjectType() {
      return FilterRegistrationBean.class;
   }
}
