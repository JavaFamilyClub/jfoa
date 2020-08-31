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

   default <R extends Comparable<R>> void export(HttpServletResponse response,
                                                 ExportType exportType,
                                                 DaoFilter<R> filter)
      throws Exception
   {
      switch(exportType) {
         case Excel:
         case Excel_2003:
            exportExcel(response, exportType, filter);
            break;
         default:
      }
   }

   <R extends Comparable<R>> void exportExcel(HttpServletResponse response,
                                              ExportType exportType, DaoFilter<R> filter) throws Exception;
}
