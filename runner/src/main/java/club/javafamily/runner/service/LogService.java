package club.javafamily.runner.service;

import club.javafamily.runner.common.table.filter.ExportFilter;
import club.javafamily.runner.domain.Log;

import java.util.List;

public interface LogService {
   void insertLog(Log log);

   List<Log> getAll();
}
