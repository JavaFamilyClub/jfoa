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
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import java.util.List;

/**
 * 1. view control
 * 2. locale control. default is <tt>Locale.US</tt>
 * 3. interceptor control
 * 4. response body serialization
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

   /* =================== resolve server i18n =================== */

   // CookieLocaleResolver
   @Bean
   public LocaleResolver localeResolver() {
      CookieLocaleResolver localeResolver = new CookieLocaleResolver();
      localeResolver.setCookieName(I18N_COOKIE_NAME);

      LOGGER.info("I18n cookie name: {}", I18N_COOKIE_NAME);

      // default locale
      localeResolver.setDefaultLocale(I18nUtil.DEFAULT_LOCALE);
      localeResolver.setCookieMaxAge(3600); // cookie valid date

      return localeResolver;
   }

   @Bean
   public LocaleChangeInterceptor localeChangeInterceptor() {
      LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
      // locale parameter name
      lci.setParamName(I18N_PARAM_NAME);

      LOGGER.info("I18n parameter name: {}", I18N_PARAM_NAME);

      return lci;
   }

   /* =================== resolved controller return String.=================== */
   @Override
   public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
      configurer.enable();
   }

   @Override
   public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
      converters.add(jsonMessageConverter());
   }

   @Bean
   public ObjectMapper objectMapper() {
      ObjectMapper mapper = new ObjectMapper();
      mapper.registerModule(new Jdk8Module());
      mapper.registerModule(new JavaTimeModule());
      mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);

      return mapper;
   }

   @Bean
   public MappingJackson2HttpMessageConverter jsonMessageConverter() {
      MappingJackson2HttpMessageConverter converter =
         new MappingJackson2HttpMessageConverter();
      converter.setObjectMapper(objectMapper());

      return converter;
   }

   @Override
   public void configurePathMatch(PathMatchConfigurer configurer) {
      // .com in name causing json return type to be rejected
      configurer.setUseSuffixPatternMatch(false);
   }

   @Override
   public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
      // .com in name causing json return type to be rejected
      configurer.favorPathExtension(false);
   }

   private static final String I18N_COOKIE_NAME = "jfLocaleCookie";
   private static final String I18N_PARAM_NAME = "jfLang";

   private static final Logger LOGGER = LoggerFactory.getLogger(WebMvcConfig.class);
}
