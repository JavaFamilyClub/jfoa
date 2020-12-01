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

import club.javafamily.runner.common.model.amqp.SubjectRequestChangeVoteMessage;
import club.javafamily.runner.common.service.AmqpService;
import club.javafamily.runner.common.service.RedisClient;
import club.javafamily.runner.domain.*;
import club.javafamily.runner.dto.VoteDto;
import club.javafamily.runner.enums.VoteOperatorStatus;
import club.javafamily.runner.service.*;
import club.javafamily.runner.util.SecurityUtil;
import club.javafamily.runner.web.portal.model.SubjectRequestVoteDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SubjectVoteService {

   @Autowired
   public SubjectVoteService(AmqpService amqpService,
                             CustomerService customerService,
                             RedisClient<Integer> redisClient,
                             SubjectRequestVoteService voteService,
                             SubjectRequestService subjectRequestService)
   {
      this.redisClient = redisClient;
      this.voteService = voteService;
      this.amqpService = amqpService;
      this.customerService = customerService;
      this.subjectRequestService = subjectRequestService;
   }

   public SubjectRequestVoteDto getSubjectVoteDto(String ip, int id) {
      int support = getVoteCurrentCount(id, true);
      int oppose = getVoteCurrentCount(id, false);

      Customer user = customerService.getCurrentCustomer();
      String account = user != null ? user.getAccount() : null;
      String opKey = convertOpKey(ip, id, account);
      Integer op = redisClient.get(opKey);

      return new SubjectRequestVoteDto(support, oppose,
         SUPPORT_FLAG.equals(op), OPPOSE_FLAG.equals(op));
   }

   /**
    * @param id subject request id, and vote id.(id is same)
    * @param support is support
    */
   public void changeVote(String ip, int id, boolean support) {
      Customer user = customerService.getCurrentCustomer();
      String account = user != null ? user.getAccount() : null;

      amqpService.sendSubjectRequestChangeVoteMessage(
         new SubjectRequestChangeVoteMessage(id, ip, support, account));
   }

   public void processChangeVote(SubjectRequestChangeVoteMessage message) {
      String ip = message.getIp();
      Integer id = message.getId();
      String account = message.getAccount();
      boolean support = message.isSupport();

      String opKey = convertOpKey(ip, id, account);
      String countKey = convertCountKey(id, support);

      Integer op = redisClient.get(opKey);

      VoteOperatorStatus opStatus = getOpNumber(op, support);

      if(opStatus == VoteOperatorStatus.Conflict) {
         // invalid
         LOGGER.warn("Invalid vote opStatus: {}", opStatus);
         return;
      }

      op = support ? SUPPORT_FLAG : OPPOSE_FLAG;

      // 1. make db data setting to redis
      // 2. setting value that key is not exist is 0.
      int oldCount = getVoteCurrentCount(id, support);

      // allow op
      if(opStatus == VoteOperatorStatus.Allow) {
         redisClient.incr(countKey, CACHED_TIME);
         redisClient.set(opKey, op, CACHED_TIME);
      }
      else {
         // clear op status, opStatus is again
         redisClient.decr(countKey, CACHED_TIME);
         redisClient.delete(opKey);
      }
   }

   public int getVoteCurrentCount(int id, boolean support) {
      String countKey = convertCountKey(id, support);
      Integer count = getCachedCount(countKey);

      if(count == null) {
         SubjectRequestVote subjectRequestVote;

         try{
            subjectRequestVote = voteService.get(id);
         }
         catch(Exception e) {
            LOGGER.error("Get real vote error: ", e);
            return -1;
         }

         if(subjectRequestVote == null) {
            count = 0;
         }
         else {
            count = support ? subjectRequestVote.getSupport()
               : subjectRequestVote.getOppose();
            count = count == null || count < 0 ? 0 : count;
         }

         if(getCachedCount(countKey) == null) {
            redisClient.set(countKey, count, CACHED_TIME);
         }
         else {
            count = getCachedCount(countKey);
            LOGGER.warn("Double check for getting cached count failed. key is {}", countKey);
         }
      }

      return count;
   }

   /**
    * Getting cached count.
    * @param id vote id
    * @param support is support
    * @return count. null if no cached.
    */
   public Integer getCachedCount(int id, boolean support) {
      String countKey = convertCountKey(id, support);

      return getCachedCount(countKey);
   }

   /**
    * Getting cached count.
    * @param countKey key
    * @return count. null if no cached.
    */
   public Integer getCachedCount(String countKey) {
      Integer cachedCount = redisClient.get(countKey);

      if(cachedCount != null && cachedCount < 0) {
         LOGGER.warn("This vote count({}) is invalid, remove it: {}", cachedCount, countKey);
         deleteCachedCount(countKey);
      }

      return cachedCount;
   }

   /**
    * check op is invalid.
    */
   private VoteOperatorStatus getOpNumber(Integer op, boolean support) {
      if(op == null) {
         // allow, first op
         return VoteOperatorStatus.Allow;
      }
      else if((Objects.equals(op, SUPPORT_FLAG) && !support)
         || (Objects.equals(op, OPPOSE_FLAG) && support))
      {
         // conflict op
         return VoteOperatorStatus.Conflict;
      }

      // again op
      return VoteOperatorStatus.Again;
   }

   /**
    * convert cached data key.
    * @param id vote id
    * @param support is support
    */
   private String convertCountKey(int id, boolean support) {
      return VOTE_COUNT_PREFIX + id + VOTE_CACHE_SEPARATOR + support;
   }

   /**
    * parse count vote dto.
    * @param countKey for example: sr-vote-count__id__support
    */
   private VoteDto parseCountKey(String countKey) {
      if(StringUtils.isEmpty(countKey)
         || !countKey.startsWith(VOTE_COUNT_PREFIX))
      {
         return null;
      }

      String[] parts = countKey.split(VOTE_CACHE_SEPARATOR);

      if(parts.length < 3) {
         return null;
      }

      return new VoteDto(Integer.parseInt(parts[1]),
         Boolean.parseBoolean(parts[2]));
   }

   /**
    * Getting count keys
    */
   public Set<String> countKeys() {
      return redisClient.prefixKeys(VOTE_COUNT_PREFIX);
   }

   public void deleteAllCachedVoteCount() {
      Set<String> keys = countKeys();

      redisClient.delete(keys);
   }

   /**
    * Getting count ids.
    */
   public Set<Integer> countIds() {
      Set<String> countKeys = countKeys();

      return countKeys.stream()
         .map(this::parseCountKey)
         .filter(Objects::nonNull)
         .map(VoteDto::getId)
         .collect(Collectors.toSet());
   }

   /**
    * Delete count by id. include support and oppose.
    * @param id vote id
    */
   public void delCount(int id) {
      redisClient.delete(convertCountKey(id, true));
      redisClient.delete(convertCountKey(id, false));
   }

   public void deleteCachedCount(int id, boolean support) {
      String cacheKey = convertCountKey(id, support);

      deleteCachedCount(cacheKey);
   }

   public void deleteCachedCount(String countKey) {
      redisClient.delete(countKey);
   }

   /**
    * Convert cached op key
    * @param ip ip address
    * @param id vote id
    * @param account user account or anonymous. nullable
    */
   private String convertOpKey(String ip, int id, String account) {
      account = Objects.toString(account, SecurityUtil.Anonymous);
      return VOTE_OP_PREFIX + ip + VOTE_CACHE_SEPARATOR
         + account + VOTE_CACHE_SEPARATOR + id;
   }

   public void syncCachedVoteToDb(Map<String, Object> result) {
      Set<Integer> redisIds = countIds();

      if(CollectionUtils.isEmpty(redisIds)) {
         LOGGER.info("Empty redis ids.");

         result.put("success", true);
         result.put("msg", "Empty redis ids.");

         return;
      }

      Integer count;
      SubjectRequestVote vote;

      for(Integer id : redisIds) {
         vote = voteService.get(id);

         if(vote == null) {
            SubjectRequest subjectRequest = subjectRequestService.get(id);

            if(subjectRequest == null) {
               delCount(id);
               LOGGER.warn("Vote's subject request is empty. vote id: {}", id);
               continue;
            }

            vote = new SubjectRequestVote(subjectRequest);
         }

         boolean invalid = false;

         count = getCachedCount(id, true);

         if(count != null && count < 0) { // == 0 may be cancel op
            deleteCachedCount(id, true);
            invalid = true;
            LOGGER.warn("Vote's support count is invalid. " +
               "vote id: {}, count: {}", id, count);
         }

         vote.setSupport(count);

         count = getCachedCount(id, false);

         if(count != null && count < 0) {
            deleteCachedCount(id, false);
            invalid = true;

            LOGGER.warn("Vote's oppose count is invalid. " +
               "vote id: {}, count: {}", id, count);
         }

         if(invalid) {
            continue;
         }

         vote.setOppose(count);

         voteService.saveOrUpdate(vote);
      }

      result.put("success", true);
   }

   private static final int CACHED_TIME = 24 * 60 * 60; // one day
   private static final Integer SUPPORT_FLAG = 1;
   private static final Integer OPPOSE_FLAG = 0;
   private static final String VOTE_CACHE_PREFIX = "sr-vote-";
   private static final String VOTE_CACHE_SEPARATOR = "__";
   private static final String VOTE_COUNT_PREFIX = VOTE_CACHE_PREFIX + "count" + VOTE_CACHE_SEPARATOR;
   private static final String VOTE_OP_PREFIX = VOTE_CACHE_PREFIX + "op" + VOTE_CACHE_SEPARATOR;

   private final RedisClient<Integer> redisClient;
   private final CustomerService customerService;
   private final SubjectRequestVoteService voteService;
   private final AmqpService amqpService;
   private final SubjectRequestService subjectRequestService;

   private static final Logger LOGGER = LoggerFactory.getLogger(SubjectVoteService.class);
}
