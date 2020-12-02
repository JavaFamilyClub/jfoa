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

package club.javafamily.runner.common.service.impl;

import club.javafamily.commons.lens.ExportTableLens;
import club.javafamily.commons.utils.ExcelUtil;
import club.javafamily.commons.utils.ExportUtil;
import club.javafamily.runner.common.service.Exporter;
import club.javafamily.commons.enums.ExportType;
import org.apache.poi.hssf.usermodel.HSSFWorkbookFactory;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbookFactory;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

@Service("excelExporter")
public class ExcelExporter implements Exporter {

   @Override
   public boolean isAccept(ExportType exportType) {
      return exportType == ExportType.Excel || exportType == ExportType.Excel_2003;
   }

   @Override
   public void export(ExportTableLens tableLens,
                      HttpServletResponse response,
                      ExportType exportType)
      throws Exception
   {
      ServletOutputStream out = response.getOutputStream();

      Workbook workbook;

      if(exportType == ExportType.Excel) {
         workbook = XSSFWorkbookFactory.createWorkbook();
      }
      else {
         workbook = HSSFWorkbookFactory.createWorkbook();
      }

      Sheet sheet = workbook.createSheet(tableLens.getTableName());
      int columnCount = tableLens.getColCount();

      for(int i = 0; i < columnCount; i++) {
         sheet.setColumnWidth(i, ExcelUtil.DEFAULT_COL_WIDTH);
      }

      CreationHelper helper = workbook.getCreationHelper();

      // create title
      ExcelUtil.createTitleRow(workbook, sheet, tableLens);

      // create header
      Cell cell;
      int rowIndex = 1; // title row
      Row dataRow;

      for(int r = 0; r < tableLens.getRowCount(); r++) {
         dataRow = ExcelUtil.createRow(workbook, sheet, rowIndex++);

         for(int c = 0; c < columnCount; c++) {
            cell = ExcelUtil.createCell(tableLens, workbook, dataRow, r, c);
            ExcelUtil.fillData(helper, cell, tableLens.getObject(r, c).getValue());
         }
      }

      String fileName = tableLens.getTableName();
      fileName = ExcelUtil.buildExcelName(fileName, exportType);
      ExportUtil.writeDownloadHeader(response, fileName);

      workbook.write(out);
      out.flush();
   }

}
