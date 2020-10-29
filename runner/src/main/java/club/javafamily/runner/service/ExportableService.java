package club.javafamily.runner.service;

import club.javafamily.runner.common.filter.DaoFilter;
import club.javafamily.runner.common.filter.Filter;
import club.javafamily.runner.common.table.lens.TableLens;
import club.javafamily.runner.enums.ExportType;

import javax.servlet.http.HttpServletResponse;

public interface ExportableService {

   /**
    * get table lens
    */
   <R extends Comparable<R>> TableLens getTableLens(DaoFilter<R> filter);

   default TableLens getTableLens() {
      return getTableLens(null);
   }
}
