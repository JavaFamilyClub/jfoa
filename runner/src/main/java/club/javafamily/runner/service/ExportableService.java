package club.javafamily.runner.service;

import club.javafamily.runner.common.filter.Filter;
import club.javafamily.runner.common.table.lens.TableLens;
import club.javafamily.runner.enums.ExportType;

import javax.servlet.http.HttpServletResponse;

public interface ExportableService {

   /**
    * get table lens
    */
   TableLens getTableLens();

   default void export(HttpServletResponse response,
                       ExportType exportType,
                       Filter filter)
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

   void exportExcel(HttpServletResponse response, ExportType exportType, Filter filter) throws Exception;
}
