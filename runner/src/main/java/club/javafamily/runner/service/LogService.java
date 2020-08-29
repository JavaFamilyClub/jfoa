package club.javafamily.runner.service;

import club.javafamily.runner.common.filter.DaoFilter;
import club.javafamily.runner.domain.Log;

import java.util.List;

public interface LogService extends ExportableService {
   void insertLog(Log log);

   default List<Log> getAll() {
      return this.getAll(null);
   }

   <T extends Comparable<T>> List<Log> getAll(DaoFilter<T> filter);
}
