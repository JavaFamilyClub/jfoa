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

package club.javafamily.runner.util;

import club.javafamily.runner.common.table.lens.ExportTableLens;
import club.javafamily.runner.enums.ExportType;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class ExcelUtil {

   public static final int TITLE_ROW_INDEX = -1;

   public static final int DEFAULT_ROW_HEIGHT = 25;
   public static final int DEFAULT_COL_WIDTH = 20 * 256;
   public static final int DEFAULT_TITLE_HEIGHT = 35;

   private ExcelUtil() {}

   public static Cell createTitleRow(Workbook workbook,
                                     Sheet sheet,
                                     ExportTableLens tableLens)
   {
      assert tableLens != null;
      int colCount = tableLens.getColCount();

      Row title = createRow(workbook, sheet, 0);
      title.setHeightInPoints(DEFAULT_TITLE_HEIGHT);
      int colIndex = colCount / 2 - 1;
      Cell cell = null, temp;

      for (int i = 0; i < colCount; i++) {
         temp = createCell(null, workbook, title, TITLE_ROW_INDEX, i);

         if(i == colIndex) {
            cell = temp;
         }
      }

      cell.setCellValue(tableLens.getTableName());

      int span = 2 + colCount % 2 - 1;
      sheet.addMergedRegion(new CellRangeAddress(0, 0, colIndex, colIndex + span));

      java.awt.Font titleFont = tableLens.getTitleFont();
      java.awt.Color fontColor = tableLens.getFontColor();

      CellStyle cellStyle = cell.getCellStyle();
      Font font = workbook.createFont();
      font.setBold(titleFont.isBold());
      font.setColor(convertColor(fontColor));
      font.setFontHeightInPoints(((short) titleFont.getSize()));
      cellStyle.setFont(font);

      cell.setCellStyle(cellStyle);

      return cell;
   }

   public static Row createRow(Workbook workbook,
                               Sheet sheet,
                               int rowIndex)
   {
      Row row = sheet.createRow(rowIndex);

      row.setHeightInPoints(DEFAULT_ROW_HEIGHT);

      CellStyle rowStyle = workbook.createCellStyle();

      row.setRowStyle(rowStyle);

      return row;
   }

   public static short convertColor(java.awt.Color color) {
      // TODO fill color types.
      if(color.equals(java.awt.Color.LIGHT_GRAY)) {
         return IndexedColors.GREY_25_PERCENT.getIndex();
      }
      else if(color.equals(java.awt.Color.ORANGE)) {
         return IndexedColors.ORANGE.getIndex();
      }

      return IndexedColors.WHITE.index;
   }

   private static void setCellBorder(CellStyle cellStyle) {
      cellStyle.setBorderBottom(BorderStyle.THIN);
      cellStyle.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
      cellStyle.setBorderLeft(BorderStyle.THIN);
      cellStyle.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
      cellStyle.setBorderRight(BorderStyle.THIN);
      cellStyle.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
      cellStyle.setBorderTop(BorderStyle.THIN);
      cellStyle.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
   }

   public static Cell createCell(ExportTableLens tableLens,
                                 Workbook workbook,
                                 Row row,
                                 int r,
                                 int column)
   {
      Cell cell = row.createCell(column);
      CellStyle cellStyle = workbook.createCellStyle();

      if(tableLens != null) {
         // font
         java.awt.Font cellFont = tableLens.getFont(r, column);
         Font font = workbook.createFont();

         if(cellFont.isBold()) {
            font.setBold(true);
         }

         font.setFontName(cellFont.getName());

         cellStyle.setFont(font);

         // background
         cellStyle.setFillForegroundColor(
            convertColor(tableLens.getBackground(r, column)));
         cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
      }

      // alignment
      cellStyle.setAlignment(HorizontalAlignment.CENTER);
      cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
      setCellBorder(cellStyle);

      cell.setCellStyle(cellStyle);

      return cell;
   }

   public static void fillData(CreationHelper helper,
                               Cell cell,
                               Object data)
   {
      if(data == null || cell == null) {
         return;
      }

      CellStyle cellStyle = cell.getCellStyle();

      if(data instanceof String) {
         cell.setCellValue(data.toString());
      }
      else if(data instanceof Integer) {
         cell.setCellValue(Integer.parseInt(data.toString()));
      }
      else if(data instanceof Double) {
         cell.setCellValue(Double.parseDouble(data.toString()));
      }
      else if(data instanceof Date) {
         cell.setCellValue(((Date) data));
         cellStyle.setDataFormat(helper.createDataFormat()
            .getFormat(Tool.DEFAULT_DATETIME_FORMAT));
      }

      cell.setCellStyle(cellStyle);
   }

   public static String buildExcelName(String fileName, ExportType exportType) {
      LocalDateTime localDateTime = LocalDateTime.now();
      fileName = fileName
         + localDateTime.format(DateTimeFormatter.ofPattern(Tool.DEFAULT_DATETIME_FORMAT))
         + exportType.getSuffix();

      return fileName;
   }
}
