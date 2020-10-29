package club.javafamily.runner.service;

import club.javafamily.runner.common.filter.DaoFilter;
import club.javafamily.runner.domain.Log;
import club.javafamily.runner.enums.ExportType;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface LogService extends ExportableService {
   void insertLog(Log log);

   default List<Log> getAll() {
      return this.getAll(null);
   }

   <T extends Comparable<T>> List<Log> getAll(DaoFilter<T> filter);

   <R extends Comparable<R>> void export(HttpServletResponse response,
                                         ExportType exportType,
                                         DaoFilter<R> filter) throws Exception;
}
