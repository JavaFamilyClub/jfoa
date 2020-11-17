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

import club.javafamily.runner.tasks.SyncRedisAndDbJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfig {

   @Bean
   public JobDetail syncRedisToDbJobDetail() {
      return JobBuilder.newJob(SyncRedisAndDbJob.class)
         .withIdentity(SyncRedisAndDbJob.class.getName(), JF_INTERNAL_GROUP)
         .storeDurably()
         .build();
   }

   @Bean
   public Trigger syncRedisToDbJobTrigger() {
      CronScheduleBuilder cron
         = CronScheduleBuilder.cronSchedule("15 * * * * ?");

      return TriggerBuilder.newTrigger()
         .forJob(syncRedisToDbJobDetail())
         .withIdentity(SyncRedisAndDbJob.class.getName(), JF_INTERNAL_GROUP)//给Trigger起个名字
         .withSchedule(cron)
         .build();
   }

   private static final String JF_INTERNAL_GROUP = "jf-internal";
}
