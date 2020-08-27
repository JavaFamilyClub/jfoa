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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

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

   private static final Logger LOGGER = LoggerFactory.getLogger(WebMvcConfig.class);
}
