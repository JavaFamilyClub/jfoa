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

package club.javafamily.runner.service;

import club.javafamily.runner.domain.SubjectRequest;
import club.javafamily.runner.domain.SubjectRequestVote;
import org.junit.Before;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Date;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SubjectRequestVoteServiceTests {

   @Autowired
   private SubjectRequestVoteService voteService;

   @Autowired
   private SubjectRequestService subjectRequestService;

   @Before
   public void init() {
   }

   @RepeatedTest(value = 5, name = "insert subject request for test vote.")
   @Order(1)
   public void insertSubjectRequest() {
      SubjectRequest sr = new SubjectRequest();
      sr.setSubject("Test Subject " + LocalDateTime.now());
      sr.setDescription("Test Desc");
      sr.setCreateDate(new Date());

      Integer id = subjectRequestService.insert(sr);

      Assertions.assertTrue(id > 0, "Insert SubjectRequest Error");
   }

   @RepeatedTest(2)
   @Order(2)
   public void testSaveOrUpdateVote() {
      int voteId = 4;

      SubjectRequestVote vote = voteService.get(voteId);

      if(vote == null) {
         vote = new SubjectRequestVote();
         vote.setSubjectRequest(subjectRequestService.get(voteId));
      }

      vote.setOppose((int) (Math.random() * 100));
      vote.setSupport((int) (Math.random() * 100));

      voteService.saveOrUpdate(vote);
   }
}
