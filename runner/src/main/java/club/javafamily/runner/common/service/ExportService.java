/*
 * Copyright (c) 2020, JavaFamily Technology Corp, All Rights Reserved.
 *
 * The software and information contained herein are copyrighted and
 * proprietary to JavaFamily Technology Corp. This software is furnished
 * pursuant to a written license agreement and may be used, copied,
 * transmitted, and stored only in accordance with the terms of such
 * license and with the inclusion of the above copyright notice. Please
 * refer to the file "COPYRIGHT" for further copyright and licensing
 * information. This software and information or any other copies
 * thereof may not be provided or otherwise made available to any other
 * person.
 */

package club.javafamily.runner.common.service;

import club.javafamily.commons.lens.ExportTableLens;
import club.javafamily.runner.common.MessageException;
import club.javafamily.commons.enums.ExportType;
import club.javafamily.runner.util.I18nUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
public class ExportService {

   @Autowired
   public ExportService(List<Exporter> exporters) {
      this.exporters = exporters;
   }

   public void export(ExportTableLens tableLens,
                      HttpServletResponse response,
                      ExportType exportType)
      throws Exception
   {

      Exporter exporter = exporters.stream()
         .filter(tmpExporter -> tmpExporter.isAccept(exportType))
         .findFirst()
         .orElse(null);

      if(exporter == null) {
         throw new MessageException(
            I18nUtil.getString("em.audit.noExporterError", exportType.getLabel()));
      }

      exporter.export(tableLens, response, exportType);
   }

   private List<Exporter> exporters;
}
