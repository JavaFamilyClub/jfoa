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

import club.javafamily.runner.util.I18nUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

/**
 * 1. view control
 * 2. locale control. default is <tt>Locale.US</tt>
 * 3. interceptor control
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

   @Override
   public void addViewControllers(ViewControllerRegistry registry) {
      registry.addRedirectViewController("/", "/app/index.html");
      registry.addViewController("/index.html").setViewName("index");
      registry.addViewController("/login.html").setViewName("login");
      registry.addViewController("/login").setViewName("login");
      registry.addViewController("/signupSuccess").setViewName("signupSuccess");
      registry.addViewController("/verifyResult").setViewName("verifyResult");
   }

   @Override
   public void addInterceptors(InterceptorRegistry registry) {
      // add locale parameter interceptor
      registry.addInterceptor(localeChangeInterceptor());
   }

   // CookieLocaleResolver
   @Bean
   public LocaleResolver localeResolver() {
      CookieLocaleResolver localeResolver = new CookieLocaleResolver();
      localeResolver.setCookieName("jfLocaleCookie");
      // default locale
      localeResolver.setDefaultLocale(I18nUtil.DEFAULT_LOCALE);
      localeResolver.setCookieMaxAge(3600); // cookie valid date

      return localeResolver;
   }

   @Bean
   public LocaleChangeInterceptor localeChangeInterceptor() {
      LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
      // locale parameter name
      lci.setParamName("jfLang");

      return lci;
   }

   private static final Logger LOGGER = LoggerFactory.getLogger(WebMvcConfig.class);
}
