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

import club.javafamily.runner.annotation.Audit;
import club.javafamily.runner.annotation.AuditObject;
import club.javafamily.runner.dao.SubjectRequestDao;
import club.javafamily.runner.domain.SubjectRequest;
import club.javafamily.runner.enums.ResourceEnum;
import club.javafamily.runner.service.SubjectRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("subjectRequestService")
public class SubjectRequestServiceImpl implements SubjectRequestService {

   @Autowired
   public SubjectRequestServiceImpl(SubjectRequestDao subjectRequestDao) {
      this.subjectRequestDao = subjectRequestDao;
   }

   @Transactional(readOnly = true)
   @Override
   public List<SubjectRequest> getList() {
      return subjectRequestDao.getAll();
   }

   @Audit(ResourceEnum.SubjectRequest)
   @Transactional
   @Override
   public Integer insert(@AuditObject("getSubject()") SubjectRequest subjectRequest) {
      return subjectRequestDao.insert(subjectRequest);
   }

   private final SubjectRequestDao subjectRequestDao;
}