package club.javafamily.runner.service.impl;

import club.javafamily.runner.common.service.ExcelService;
import club.javafamily.runner.common.table.filter.ExportFilter;
import club.javafamily.runner.common.table.lens.DefaultTableLens;
import club.javafamily.runner.common.table.lens.TableLens;
import club.javafamily.runner.dao.LogDao;
import club.javafamily.runner.domain.Log;
import club.javafamily.runner.enums.ExportType;
import club.javafamily.runner.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.metamodel.Metamodel;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.List;

@Service("logService")
public class LogServiceImpl implements LogService, ExportableService {

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

   @Override
   public TableLens getTableLens() {
      Metamodel metadata = logDao.getMetadata();

      return null;
   }

   @Transactional(readOnly = true)
   @Override
   public void exportExcel(HttpServletResponse response, ExportType exportType, ExportFilter filter) throws Exception {
      TableLens tableLens = getTableLens();
      excelService.export(tableLens, response, exportType, filter, "JavaFamily Audit");
   }

   @Autowired
   public LogServiceImpl(LogDao logDao, ExcelService excelService) {
      this.logDao = logDao;
      this.excelService = excelService;
   }

   private final LogDao logDao;
   private final ExcelService excelService;
}
