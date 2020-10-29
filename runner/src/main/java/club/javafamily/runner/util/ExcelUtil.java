package club.javafamily.runner.util;

import club.javafamily.runner.enums.ExportType;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class ExcelUtil {

   public static String Excel_xls = ".xls";
   public static String Excel_xlsx = ".xlsx";

   public static final int DEFAULT_ROW_HEIGHT = 25;
   public static final int DEFAULT_COL_WIDTH = 20 * 256;
   public static final int DEFAULT_TITLE_HEIGHT = 35;

   private ExcelUtil() {}

   public static Cell createTitleRow(Workbook workbook,
                                     Sheet sheet,
                                     String titleLabel,
                                     int colCount)
   {
      Row title = createRow(workbook, sheet, 0);
      title.setHeightInPoints(DEFAULT_TITLE_HEIGHT);
      int colIndex = colCount / 2 - 1;
      Cell cell = null, temp;

      for (int i = 0; i < colCount; i++) {
         temp = createCell(workbook, title, i);

         if(i == colIndex) {
            cell = temp;
         }
      }

      cell.setCellValue(titleLabel);

      int span = 2 + colCount % 2 - 1;
      sheet.addMergedRegion(new CellRangeAddress(0, 0, colIndex, colIndex + span));

      CellStyle cellStyle = cell.getCellStyle();
      Font font = workbook.createFont();
      font.setBold(true);
      font.setColor(IndexedColors.ORANGE.getIndex());
      font.setFontHeightInPoints(((short) 20));
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

   public static Cell createHeaderCell(Workbook workbook,
                                       Row row,
                                       int column)
   {
      Cell cell = createCell(workbook, row, column);

      CellStyle cellStyle = cell.getCellStyle();

      Font font = workbook.createFont();
      font.setBold(true);
      cellStyle.setFont(font);

      cellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
      cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

      return cell;
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

   public static Cell createCell(Workbook workbook,
                                     Row row,
                                     int column)
   {
      Cell cell = row.createCell(column);
      CellStyle cellStyle = workbook.createCellStyle();

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
