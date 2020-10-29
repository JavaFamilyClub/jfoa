package club.javafamily.runner.common.service.impl;

import club.javafamily.runner.common.service.Exporter;
import club.javafamily.runner.common.table.lens.TableLens;
import club.javafamily.runner.enums.ExportType;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

@Service("pdfExporter")
public class PDFExporter implements Exporter {
   @Override
   public boolean isAccept(ExportType exportType) {
      return exportType == ExportType.PDF;
   }

   @Override
   public void export(TableLens tableLens, HttpServletResponse response,
                      ExportType exportType, String fileName)
      throws Exception
   {
      // TODO export PDF
   }
}
