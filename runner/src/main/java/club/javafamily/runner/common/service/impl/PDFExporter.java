package club.javafamily.runner.common.service.impl;

import club.javafamily.runner.common.service.Exporter;
import club.javafamily.runner.common.table.lens.ExportTableLens;
import club.javafamily.runner.enums.ExportType;
import club.javafamily.runner.util.ExportUtil;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.UnitValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;

import static club.javafamily.runner.common.table.lens.LensTool.DEFAULT_HEADER_FONT;

/**
 * iText7 export pdf support.
 * See: https://kb.itextpdf.com/home/it7kb/ebooks/itext-7-jump-start-tutorial-for-java/chapter-1-introducing-basic-building-blocks
 */
@Service("pdfExporter")
public class PDFExporter implements Exporter {

   private static PdfFont DEFAULT_PDF_TEXT_FONT;
   private static PdfFont DEFAULT_PDF_BOLD_FONT;

   static {
      try {
         PdfFontFactory.registerSystemDirectories();
         DEFAULT_PDF_TEXT_FONT = PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);
         DEFAULT_PDF_BOLD_FONT = PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);
      }
      catch(Exception ignore) {
      }
   }

   @Override
   public boolean isAccept(ExportType exportType) {
      return exportType == ExportType.PDF;
   }

   @Override
   public void export(ExportTableLens tableLens,
                      HttpServletResponse response,
                      ExportType exportType)
      throws Exception
   {
      // 创建一个指向文件或者 out 流的 PDFWriter, 该 writer 会监听 PdfDocument
      PdfWriter pdfWriter = new PdfWriter(response.getOutputStream());
      // 创建 PdfDocument 代表要创建的 PDF 文件, 管理要写入的内容和相关信息
      PdfDocument pdf = new PdfDocument(pdfWriter);
      // 创建一个 A4 大小的文档, 代表 PDF 的一页
      Document document = new Document(pdf, PageSize.A4);

      // 设置页边距, 默认为 36
      document.setMargins(20, 20, 20, 20);

      // 创建一个 Table 元素, 并初始化 Table 每列的宽度
      // 注意该 table 的 <code>colWidth</code> 指定的仅仅是各个列的相对宽度, 而不是具体宽度
      int colCount = tableLens.getColCount();
      float[] colWidth = new float[colCount];

      for(int i = 0; i < colCount; i++) {
         colWidth[i] = tableLens.getColWidth(i);
      }

      Table table = new Table(colWidth);

      // 指定 table 相对 Document 的宽度.
      // <code>UnitValue.createPercentValue(100)</code> 表示 100%(排除页边距).
      table.setWidth(UnitValue.createPercentValue(100));

      // 填充 table
      for(int i = 0; i < tableLens.getRowCount(); i++) {
         for(int j = 0; j < tableLens.getColCount(); j++) {
            fillCellData(table, tableLens, i, j);
         }
      }

      // 将 table 写入 Document
      document.add(table);

      String fileName = tableLens.getTableName() + exportType.getSuffix();
      ExportUtil.writeDownloadHeader(response, fileName);

      // 关闭文档流
      document.close();
   }

   /**
    * fill cell data.
    * @param table pdf table
    * @param tableLens data source
    * @param row row index
    * @param col col index
    */
   private void fillCellData(Table table, ExportTableLens tableLens, int row, int col) {
      boolean isHeader = tableLens.isHeader(row, col);
      club.javafamily.runner.common.table.cell.Cell cell = tableLens.getObject(row, col);

      Font cellFont = tableLens.getFont(row, col);
      PdfFont pdfFont = getFont(cellFont, isHeader);

      if (isHeader) {
         // <code>Paragraph</code> 代表一个段落, 传入字符串就可以写入一个文本段落到 Document,
         // 传入 <code>Cell</code> 就会画一个 cell.
         table.addHeaderCell(new Cell().add(
            new Paragraph(ExportUtil.toString(cell)).setFont(pdfFont)));
      } else {
         table.addCell(new Cell().add(
            new Paragraph(ExportUtil.toString(cell)).setFont(pdfFont)));
      }
   }

   private PdfFont getFont(Font font, boolean isHeader) {
      try {
         return PdfFontFactory.createRegisteredFont(font.getFontName());
      } catch(IOException e) {
         LOGGER.warn("Registered font is not found! {}", font.getFontName());
      }

      return isHeader ? DEFAULT_PDF_BOLD_FONT : DEFAULT_PDF_TEXT_FONT;
   }

   private static final Logger LOGGER = LoggerFactory.getLogger(PDFExporter.class);
}
