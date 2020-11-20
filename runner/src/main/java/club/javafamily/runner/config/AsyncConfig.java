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

import club.javafamily.runner.properties.ThreadPoolProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.lang.reflect.Method;
import java.util.concurrent.Executor;

@Configuration
@EnableAsync
@EnableConfigurationProperties(ThreadPoolProperties.class)
public class AsyncConfig implements AsyncConfigurer {

   @Override
   public Executor getAsyncExecutor() {
      ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

      // setting core thread pool size
      int cores = properties.getCorePoolSize();

      if(cores < 0) {
         cores = Runtime.getRuntime().availableProcessors();
      }

      executor.setCorePoolSize(cores);

      // setting max pool size
      executor.setMaxPoolSize(properties.getMaxSize());

      executor.setWaitForTasksToCompleteOnShutdown(properties.isWaitTasksComplete());
      executor.setThreadNamePrefix(properties.getPrefix());

      // Note: need call initialize
      executor.initialize();

      return executor;
   }

   @Override
   public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
      return (Throwable ex, Method method, Object... params) -> {
         LOGGER.error("Async tasks exec error!", ex);
      };
   }

   @Autowired
   public AsyncConfig(ThreadPoolProperties properties) {
      this.properties = properties;
   }

   private final ThreadPoolProperties properties;
   private static final Logger LOGGER = LoggerFactory.getLogger(AsyncConfig.class);
}
