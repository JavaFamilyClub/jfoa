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

package club.javafamily.runner.service.impl;

import club.javafamily.runner.dao.SubjectRequestVoteDao;
import club.javafamily.runner.domain.SubjectRequestVote;
import club.javafamily.runner.service.SubjectRequestVoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("subjectRequestVoteService")
public class SubjectRequestVoteServiceImpl implements SubjectRequestVoteService {

   @Transactional(readOnly = true)
   @Override
   public SubjectRequestVote get(Integer id) {
      return voteDao.get(id);
   }

   @Transactional
   @Override
   public Integer insert(SubjectRequestVote vote) {
      return voteDao.insert(vote);
   }

   @Transactional
   @Override
   public void update(SubjectRequestVote vote) {
      voteDao.update(vote);
   }

   @Transactional
   @Override
   public void delete(SubjectRequestVote vote) {
      voteDao.delete(vote);
   }

   @Autowired
   public SubjectRequestVoteServiceImpl(SubjectRequestVoteDao voteDao) {
      this.voteDao = voteDao;
   }

   private final SubjectRequestVoteDao voteDao;
}
