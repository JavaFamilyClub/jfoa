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

import club.javafamily.runner.service.SubjectRequestVoteService;
import club.javafamily.runner.web.portal.service.SubjectVoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Set;

@Component
public class SyncDatabaseSchedulerTask {

   /**
    * Synchronize Redis to DB every day at 3:30 AM
    */
   @Scheduled(cron = "* 0 30 3 * * ？")
   public void syncRedisToDb() {
      LOGGER.info("Start Sync Redis to DB at {}", LocalDateTime.now());

      Set<String> redisKeys = voteService.countKeys();

      if(CollectionUtils.isEmpty(redisKeys)) {
         LOGGER.info("Empty redis keys.");
         return;
      }

      Integer count;

      for(String cacheKey : redisKeys) {
         count = voteService.getCachedCount(cacheKey);

         if(count == null || count < 0) { // == 0 may be cancel op
            continue;
         }

         // TODO sync

      }

      LOGGER.info("Complete Sync Redis to DB at {}", LocalDateTime.now());
   }

   public SyncDatabaseSchedulerTask(SubjectVoteService voteService,
                                    SubjectRequestVoteService voteDbService)
   {
      this.voteService = voteService;
      this.voteDbService = voteDbService;
   }

   private final SubjectVoteService voteService;
   private final SubjectRequestVoteService voteDbService;

   private static final Logger LOGGER = LoggerFactory.getLogger(SyncDatabaseSchedulerTask.class);
}
