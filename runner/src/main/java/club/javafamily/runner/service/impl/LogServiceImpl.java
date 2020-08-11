package club.javafamily.runner.service.impl;

import club.javafamily.runner.dao.LogDao;
import club.javafamily.runner.domain.Log;
import club.javafamily.runner.service.LogService;
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
   public List<Log> getAll() {
      return logDao.getAll();
   }

   @Autowired
   public LogServiceImpl(LogDao logDao) {
      this.logDao = logDao;
   }

   private final LogDao logDao;
}
