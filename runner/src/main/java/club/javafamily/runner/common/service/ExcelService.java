package club.javafamily.runner.common.service;

import club.javafamily.runner.common.table.filter.Filter;
import club.javafamily.runner.common.table.lens.TableLens;
import club.javafamily.runner.enums.ExportType;
import club.javafamily.runner.util.ExcelUtil;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.stereotype.Service;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

@Service
public class ExcelService {

   public void export(TableLens tableLens,
                      HttpServletResponse response,
                      ExportType exportType,
                      Filter filter,
                      String fileName)
      throws Exception
   {
      ServletOutputStream out = response.getOutputStream();
      HSSFWorkbook workbook = HSSFWorkbookFactory.createWorkbook();
      HSSFSheet sheet = workbook.createSheet(tableLens.getDescription());
      int columnCount = tableLens.getColCount();

      for(int i = 0; i < columnCount; i++) {
         sheet.setColumnWidth(i, ExcelUtil.DEFAULT_COL_WIDTH);
      }

      HSSFCreationHelper helper = workbook.getCreationHelper();

      // create title
      ExcelUtil.createTitleRow(workbook, sheet, tableLens.getDescription(), columnCount);

      // create header
      HSSFCell cell;
      int colIndex = 0;
      int rowIndex = 1; // title row
      HSSFRow header = ExcelUtil.createRow(workbook, sheet, rowIndex++);

      for(int i = 0; i < columnCount; i++) {
         cell = ExcelUtil.createHeaderCell(workbook, header, colIndex);
         ExcelUtil.fillData(helper, cell, tableLens.getObject(0, i).getValue());
         colIndex++;
      }

      HSSFRow dataRow;

      for(int r = 1; r < tableLens.getRowCount(); r++) {
         dataRow = ExcelUtil.createRow(workbook, sheet, rowIndex++);

         for(int c = 0; c < columnCount; c++) {
            cell = ExcelUtil.createCell(workbook, dataRow, c);
            ExcelUtil.fillData(helper, cell, tableLens.getObject(r, c));
         }
      }

      workbook.write(out);

      String type = new MimetypesFileTypeMap().getContentType(fileName);

      response.setCharacterEncoding("utf-8");
      response.setContentType(type);
      String downloadFileName = new String(fileName.getBytes("utf-8"), "iso-8859-1");
      response.setHeader("Content-Disposition", "attachment;fileName=" + downloadFileName);
      out.flush();
   }

}
