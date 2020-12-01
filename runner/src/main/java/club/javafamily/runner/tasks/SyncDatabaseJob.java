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

package club.javafamily.runner.tasks;

import club.javafamily.runner.common.model.amqp.TemplateEmailMessage;
import club.javafamily.runner.common.service.AmqpService;
import club.javafamily.runner.util.SecurityUtil;
import club.javafamily.runner.web.portal.service.SubjectVoteService;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class SyncDatabaseJob extends QuartzJobBean {

   @Override
   protected void executeInternal(JobExecutionContext context)
      throws JobExecutionException
   {
      LOGGER.info("Starting Sync Redis to DB at {}", LocalDateTime.now());

      try {
         syncCachedVoteToDb();
      }
      finally {
         LOGGER.info("Complete Sync Redis to DB at {}", LocalDateTime.now());
      }
   }

   public void syncCachedVoteToDb() {
      Map<String, Object> params = new HashMap<>();
      TemplateEmailMessage message = new TemplateEmailMessage(
         SYNC_RESULT_NOTICE_TEMPLATE, SecurityUtil.Author_Email,
         "Sync Redis to DB Result", params);

      try {
         voteService.syncCachedVoteToDb(params);
      }
      catch(Exception e) {
         params.put("success", false);
         params.put("msg", e.getMessage());

         throw e;
      }
      finally {
         amqpService.sendTemplateEmailMessage(message);
      }
   }

   public SyncDatabaseJob(AmqpService amqpService,
                          SubjectVoteService voteService)
   {
      this.amqpService = amqpService;
      this.voteService = voteService;
   }

   private final AmqpService amqpService;
   private final SubjectVoteService voteService;

   private static final String SYNC_RESULT_NOTICE_TEMPLATE = "syncDbNotice";

   private static final Logger LOGGER = LoggerFactory.getLogger(SyncDatabaseJob.class);
}
