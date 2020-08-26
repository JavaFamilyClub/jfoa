package club.javafamily.runner.util;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.Date;

public class ExcelUtil {

   public static String Excel_xls = ".xls";
   public static String Excel2007_Suffix = ".xlsx";

   public static final int DEFAULT_ROW_HEIGHT = 25;
   public static final int DEFAULT_COL_WIDTH = 20 * 256;
   public static final int DEFAULT_TITLE_HEIGHT = 35;

   private ExcelUtil() {}

   public static HSSFCell createTitleRow(HSSFWorkbook workbook,
                                        HSSFSheet sheet,
                                        String titleLabel,
                                        int colCount)
   {
      HSSFRow title = createRow(workbook, sheet, 0);
      title.setHeightInPoints(DEFAULT_TITLE_HEIGHT);
      int colIndex = colCount / 2 - 1;
      HSSFCell cell = null, temp;

      for (int i = 0; i < colCount; i++) {
         temp = createCell(workbook, title, i);

         if(i == colIndex) {
            cell = temp;
         }
      }

      cell.setCellValue(titleLabel);

      int span = 2 + colCount % 2 - 1;
      sheet.addMergedRegion(new CellRangeAddress(0, 0, colIndex, colIndex + span));

      HSSFCellStyle cellStyle = cell.getCellStyle();
      HSSFFont font = workbook.createFont();
      font.setBold(true);
      font.setColor(IndexedColors.ORANGE.getIndex());
      font.setFontHeightInPoints(((short) 20));
      cellStyle.setFont(font);

      cell.setCellStyle(cellStyle);

      return cell;
   }

   public static HSSFRow createRow(HSSFWorkbook workbook,
                                   HSSFSheet sheet,
                                   int rowIndex)
   {
      HSSFRow row = sheet.createRow(rowIndex);

      row.setHeightInPoints(DEFAULT_ROW_HEIGHT);

      HSSFCellStyle rowStyle = workbook.createCellStyle();

      row.setRowStyle(rowStyle);

      return row;
   }

   public static HSSFCell createHeaderCell(HSSFWorkbook workbook,
                                           HSSFRow row,
                                           int column)
   {
      HSSFCell cell = createCell(workbook, row, column);

      HSSFCellStyle cellStyle = cell.getCellStyle();

      HSSFFont font = workbook.createFont();
      font.setBold(true);
      cellStyle.setFont(font);

      cellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
      cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

      return cell;
   }

   private static void setCellBorder(HSSFCellStyle cellStyle) {
      cellStyle.setBorderBottom(BorderStyle.THIN);
      cellStyle.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
      cellStyle.setBorderLeft(BorderStyle.THIN);
      cellStyle.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
      cellStyle.setBorderRight(BorderStyle.THIN);
      cellStyle.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
      cellStyle.setBorderTop(BorderStyle.THIN);
      cellStyle.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
   }

   public static HSSFCell createCell(HSSFWorkbook workbook,
                                     HSSFRow row,
                                     int column)
   {
      HSSFCell cell = row.createCell(column);
      HSSFCellStyle cellStyle = workbook.createCellStyle();

      cellStyle.setAlignment(HorizontalAlignment.CENTER);
      cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
      setCellBorder(cellStyle);

      cell.setCellStyle(cellStyle);

      return cell;
   }

   public static void fillData(HSSFCreationHelper helper,
                               HSSFCell cell,
                               Object data)
   {
      if(data == null || cell == null) {
         return;
      }

      HSSFCellStyle cellStyle = cell.getCellStyle();

      if(data instanceof String) {
         cell.setCellValue(data.toString());
      }
      else if(data instanceof Integer) {
         cell.setCellValue(Integer.parseInt(data.toString()));
      }
      else if(data instanceof Double) {
         cell.setCellValue(Double.valueOf(data.toString()));
      }
      else if(data instanceof Date) {
         cell.setCellValue(((Date) data));
         cellStyle.setDataFormat(helper.createDataFormat()
            .getFormat(Tool.DEFAULT_DATETIME_FORMAT));
      }

      cell.setCellStyle(cellStyle);
   }
}
