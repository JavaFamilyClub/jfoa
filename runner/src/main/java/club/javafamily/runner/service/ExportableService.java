package club.javafamily.runner.service;

import club.javafamily.runner.common.table.filter.ExportFilter;
import club.javafamily.runner.common.table.lens.TableLens;
import club.javafamily.runner.enums.ExportType;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

public interface ExportableService {

   /**
    * get table lens
    */
   TableLens getTableLens();

   default void export(HttpServletResponse response,
                       ExportType exportType,
                       ExportFilter filter)
      throws Exception
   {
      switch(exportType) {
         case Excel:
            exportExcel(response, exportType, filter);
            break;
         default:
      }
   }

   void exportExcel(HttpServletResponse response, ExportType exportType, ExportFilter filter) throws Exception;
}
