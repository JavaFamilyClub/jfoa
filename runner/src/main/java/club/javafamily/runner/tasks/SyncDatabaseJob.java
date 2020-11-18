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

import club.javafamily.runner.domain.SubjectRequest;
import club.javafamily.runner.domain.SubjectRequestVote;
import club.javafamily.runner.service.SubjectRequestService;
import club.javafamily.runner.service.SubjectRequestVoteService;
import club.javafamily.runner.web.portal.service.SubjectVoteService;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.Set;

@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class SyncDatabaseJob extends QuartzJobBean {

   @Override
   protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
      LOGGER.info("Start Sync Redis to DB at {}", LocalDateTime.now());

      Set<Integer> redisIds = voteService.countIds();

      if(CollectionUtils.isEmpty(redisIds)) {
         LOGGER.info("Empty redis ids.");
         return;
      }

      Integer count;
      SubjectRequestVote vote;

      for(Integer id : redisIds) {
         vote = voteDbService.get(id);

         if(vote == null) {
            SubjectRequest subjectRequest = subjectRequestService.get(id);

            if(subjectRequest == null) {
               voteService.delCount(id);
               LOGGER.warn("Vote's subject request is empty. vote id: {}", id);
               continue;
            }

            vote = new SubjectRequestVote(subjectRequest);
         }

         boolean invalid = false;

         count = voteService.getCachedCount(id, true);

         if(count == null || count < 0) { // == 0 may be cancel op
            voteService.resetCachedCount(id, true, 0);
            invalid = true;
            LOGGER.warn("Vote's support count is invalid. " +
               "vote id: {}, count: {}", id, count);
         }

         vote.setSupport(count);

         count = voteService.getCachedCount(id, false);

         if(count == null || count < 0) {
            voteService.resetCachedCount(id, false, 0);
            invalid = true;

            LOGGER.warn("Vote's oppose count is invalid. " +
               "vote id: {}, count: {}", id, count);
         }

         if(invalid) {
            continue;
         }

         vote.setOppose(count);

         voteDbService.saveOrUpdate(vote);
      }

      LOGGER.info("Complete Sync Redis to DB at {}", LocalDateTime.now());
   }

   public SyncDatabaseJob(SubjectVoteService voteService,
                          SubjectRequestVoteService voteDbService,
                          SubjectRequestService subjectRequestService)
   {
      this.voteService = voteService;
      this.voteDbService = voteDbService;
      this.subjectRequestService = subjectRequestService;
   }

   private final SubjectVoteService voteService;
   private final SubjectRequestVoteService voteDbService;
   private final SubjectRequestService subjectRequestService;

   private static final Logger LOGGER = LoggerFactory.getLogger(SyncDatabaseJob.class);
}
