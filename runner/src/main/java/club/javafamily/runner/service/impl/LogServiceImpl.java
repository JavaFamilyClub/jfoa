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

import club.javafamily.commons.lens.ExportTableLens;
import club.javafamily.runner.common.filter.DaoFilter;
import club.javafamily.runner.dao.LogDao;
import club.javafamily.runner.domain.Log;
import club.javafamily.runner.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("logService")
public class LogServiceImpl implements LogService {

   @Transactional
   @Override
   public void insertLog(Log log) {
      logDao.insert(log);
   }

   @Transactional(readOnly = true)
   @Override
   public <T extends Comparable<T>> List<Log> getAll(DaoFilter<T> filter) {
      return logDao.getAll(filter);
   }

   @Transactional(readOnly = true)
   @Override
   public <R extends Comparable<R>> ExportTableLens getTableLens(DaoFilter<R> filter) {
      return logDao.getTableLens(filter);
   }

   @Autowired
   public LogServiceImpl(LogDao logDao) {
      this.logDao = logDao;
   }

   private final LogDao logDao;
}
