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

package club.javafamily.runner.web.portal.service;

import club.javafamily.runner.common.service.RedisClient;
import club.javafamily.runner.dao.SubjectRequestVoteDao;
import club.javafamily.runner.domain.Customer;
import club.javafamily.runner.domain.SubjectRequestVote;
import club.javafamily.runner.service.CustomerService;
import club.javafamily.runner.service.SubjectRequestService;
import club.javafamily.runner.util.SecurityUtil;
import club.javafamily.runner.web.portal.model.SubjectRequestVoteDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.Objects;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Service
public class SubjectVoteService {

   @Autowired
   public SubjectVoteService(RedisClient<Integer> redisClient,
                             CustomerService customerService,
                             SubjectRequestService subjectRequestService,
                             SubjectRequestVoteDao voteDao,
                             HibernateTransactionManager transactionManager)
   {
      this.redisClient = redisClient;
      this.customerService = customerService;
      this.subjectRequestService = subjectRequestService;
      this.voteDao = voteDao;
      this.transactionManager = transactionManager;
   }

   public SubjectRequestVoteDto getSubjectVoteDto(String ip, int id) {
      int support = getVoteCurrentCount(id, true);
      int oppose = getVoteCurrentCount(id, false);

      Customer user = customerService.getCurrentCustomer();
      String account = user != null ? user.getAccount() : null;
      String opKey = convertOpKey(ip, id, account);
      Integer op;

      lock.readLock().lock();

      try {
         op = redisClient.get(opKey);
      }
      finally {
         lock.readLock().unlock();
      }

      return new SubjectRequestVoteDto(support, oppose,
         SUPPORT_FLAG.equals(op), OPPOSE_FLAG.equals(op));
   }

   /**
    * @param id subject request id, and vote id.(id is same)
    * @param support is support
    */
   @Async
   public void changeVote(String ip, int id, boolean support) {
      Customer user = customerService.getCurrentCustomer();
      String account = user != null ? user.getAccount() : null;

      String opKey = convertOpKey(ip, id, account);
      String countKey = convertCountKey(id, support);
      Integer op;
      int count;

      lock.readLock().lock();

      try {
         op = redisClient.get(opKey);
         count = getVoteCurrentCount(id, support);
      }
      finally {
         lock.readLock().unlock();
      }

      int opNumber = getOpNumber(op, support);

      if(opNumber == 0) {
         // invalid
         LOGGER.warn("Invalid vote opNumber: 0");
         return;
      }

      // count always is not null
      count = count + opNumber; // opNumber is 1 or -1.

      if(count < 0) {
         LOGGER.warn("Invalid vote count: {}", count);
         // invalid
         return;
      }

      op = support ? SUPPORT_FLAG : OPPOSE_FLAG;

      lock.writeLock().lock();

      try {
         redisClient.set(countKey, count, CACHED_TIME);

         if(opNumber > 0) {
            redisClient.set(opKey, op, CACHED_TIME);
         }
         else {
            // clear op status
            redisClient.delete(opKey);
         }
      }
      finally {
         lock.writeLock().unlock();
      }
   }

   private int getVoteCurrentCount(int id, boolean support) {
      String countKey = convertCountKey(id, support);
      Integer count = redisClient.get(countKey);

      if(count == null) {
         TransactionStatus transaction;
         SubjectRequestVote subjectRequestVote = null;

         try{
            transaction = transactionManager.getTransaction(transactionDefinition);
            subjectRequestVote = voteDao.get(id);
            transactionManager.commit(transaction);
         }
         catch(Exception e) {
            LOGGER.error("Get real vote error: ", e);
         }

         if(subjectRequestVote == null) {
            count = 0;
         }
         else {
            count = support ? subjectRequestVote.getSupport()
               : subjectRequestVote.getOppose();
         }

         lock.writeLock().lock();

         try {
            redisClient.set(countKey, count, CACHED_TIME);
         }
         finally {
            lock.writeLock().unlock();
         }
      }

      return count;
   }

   /**
    * check op is invalid.
    *
    */
   private int getOpNumber(Integer op, boolean support) {
      if(op == null) {
         // first op
         return 1;
      }
      else if((Objects.equals(op, SUPPORT_FLAG) && !support)
         || (Objects.equals(op, OPPOSE_FLAG) && support))
      {
         // conflict op
         return 0;
      }

      // again op
      return -1;
   }

   /**
    * convert cached data key.
    * @param id vote id
    * @param support is support
    */
   private String convertCountKey(int id, boolean support) {
      return VOTE_CACHE_PREFIX + VOTE_COUNT + id + VOTE_CACHE_SEPARATOR + support;
   }

   private String convertOpKey(String ip, int id, String account) {
      account = Objects.toString(account, SecurityUtil.Anonymous);
      return VOTE_CACHE_PREFIX + VOTE_OP + ip + VOTE_CACHE_SEPARATOR
         + account + VOTE_CACHE_SEPARATOR + id;
   }

   private static final int CACHED_TIME = 24 * 60 * 60; // one day
   private static final Integer SUPPORT_FLAG = 1;
   private static final Integer OPPOSE_FLAG = 0;
   private static final String VOTE_CACHE_PREFIX = "sr-vote-";
   private static final String VOTE_CACHE_SEPARATOR = "__";
   private static final String VOTE_COUNT = "count" + VOTE_CACHE_SEPARATOR;
   private static final String VOTE_OP = "op" + VOTE_CACHE_SEPARATOR;

   private final ReadWriteLock lock = new ReentrantReadWriteLock();

   private final RedisClient<Integer> redisClient;
   private final CustomerService customerService;
   private final SubjectRequestService subjectRequestService;

   private final SubjectRequestVoteDao voteDao;
   private final HibernateTransactionManager transactionManager;
   private final TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();

   private static final Logger LOGGER = LoggerFactory.getLogger(SubjectVoteService.class);
}
