package club.javafamily.runner.service.impl;

import club.javafamily.runner.common.service.ExportService;
import club.javafamily.runner.common.service.impl.ExcelExporter;
import club.javafamily.runner.common.filter.DaoFilter;
import club.javafamily.runner.common.table.lens.ExportTableLens;
import club.javafamily.runner.common.table.lens.TableLens;
import club.javafamily.runner.dao.LogDao;
import club.javafamily.runner.domain.Log;
import club.javafamily.runner.enums.ExportType;
import club.javafamily.runner.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
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

   @Transactional(readOnly = true)
   @Override
   public <R extends Comparable<R>> void export(HttpServletResponse response,
                                                ExportType exportType,
                                                DaoFilter<R> filter) throws Exception
   {
      ExportTableLens tableLens = getTableLens(filter);
      exportService.export(tableLens, response, exportType, "JavaFamily Audit");
   }

   @Autowired
   public LogServiceImpl(LogDao logDao, ExportService exportService) {
      this.logDao = logDao;
      this.exportService = exportService;
   }

   private final LogDao logDao;
   private final ExportService exportService;
}
