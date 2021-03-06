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

import club.javafamily.runner.dao.ArchivedSubjectDao;
import club.javafamily.runner.domain.ArchivedSubject;
import club.javafamily.runner.service.ArchivedSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("archivedSubject")
public class ArchivedSubjectServiceImpl implements ArchivedSubjectService {

   @Autowired
   public ArchivedSubjectServiceImpl(ArchivedSubjectDao archivedSubjectDao) {
      this.archivedSubjectDao = archivedSubjectDao;
   }

   @Transactional(readOnly = true)
   @Override
   public ArchivedSubject get(Integer id) {
      return archivedSubjectDao.get(id);
   }

   @Transactional
   @Override
   public Integer insert(ArchivedSubject archivedSubject) {
      return archivedSubjectDao.insert(archivedSubject);
   }

   @Transactional
   @Override
   public void saveOrUpdate(ArchivedSubject archivedSubject) {
      archivedSubjectDao.saveOrUpdate(archivedSubject);
   }

   private final ArchivedSubjectDao archivedSubjectDao;
}
