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

package club.javafamily.runner.web.admin;

import club.javafamily.runner.web.portal.service.SubjectVoteService;
import org.springframework.jmx.export.annotation.*;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Subject Request Vote's MBean
 */
@Component
@ManagedResource
public class SubjectVoteMBean {

   public SubjectVoteMBean(SubjectVoteService voteService) {
      this.voteService = voteService;
   }

   @ManagedOperation(description = "Sync subject request vote from redis to db")
   public Map<String, Object> syncCachedToDb() {
      Map<String, Object> result = new HashMap<>();

      voteService.syncCachedVoteToDb(result);

      return result;
   }

   @ManagedOperation(description = "Remove all cached subject request vote cached")
   public void destroyCachedVoteCount() {
      voteService.deleteAllCachedVoteCount();
   }

   @ManagedAttribute(description = "Cached subject request vote count redis keys")
   public Set<String> getCachedVoteCountKeys() {
      return voteService.countKeys();
   }

   @ManagedAttribute(description = "Cached subject request vote count ids")
   public Set<Integer> getCachedVoteCountIds() {
      return voteService.countIds();
   }

   private final SubjectVoteService voteService;
}
