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

package club.javafamily.runner;

import club.javafamily.runner.properties.MainServerProperties;
import club.javafamily.runner.properties.OAuthProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(
   scanBasePackages = "club.javafamily",
   exclude = {
      JpaRepositoriesAutoConfiguration.class,
      HibernateJpaAutoConfiguration.class
   }
)
@EnableTransactionManagement
@EnableCaching
@EnableScheduling
@EnableConfigurationProperties({
   OAuthProperties.class,
   MainServerProperties.class
})
public class RunnerApplication {

   public static void main(String[] args) {
      SpringApplication.run(RunnerApplication.class, args);
   }

}
