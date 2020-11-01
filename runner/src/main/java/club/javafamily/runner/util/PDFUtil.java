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
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.io.IOException;

public final class PDFUtil {

   private PDFUtil() {
   }

   private static byte[] DEFAULT_PDF_TEXT_FONT;
   private static byte[] DEFAULT_PDF_BOLD_FONT;

   static {
      try {
//         PdfFontFactory.registerSystemDirectories();
         DEFAULT_PDF_TEXT_FONT = Tool.getConfigFileData("public/fonts/simsun.ttf");
//            .getURL().getPath();
         DEFAULT_PDF_BOLD_FONT = Tool.getConfigFileData("public/fonts/simhei.TTF");
//            .getURL().getPath();
      }
      catch(Exception e) {
         e.printStackTrace();
      }
   }

   public static Color convertColor(java.awt.Color color) {
      return new DeviceRgb(color);
   }

   public static PdfFont convertFont(Font font) throws IOException {
//      try {
//         PdfFont pdfFont = PdfFontFactory.createRegisteredFont(font.getName());
//
//         return pdfFont;
//      } catch(IOException e) {
//         LOGGER.warn("Registered font is not found! {}", font.getFontName());
//      }

      // by JackLi: 字体不能为静态, 需要每次请求都创建新的,
      // 否则在多次导出时 <code>document.close()</code> 会出检查异常.
      // TODO 在 ThreadLocal 中进行缓存避免对每个 cell 都创建字体.

      return getFont(font.isBold());
   }

   private static PdfFont getFont(boolean bold) throws IOException {
      return bold
         ? PdfFontFactory.createFont(DEFAULT_PDF_BOLD_FONT, PdfEncodings.IDENTITY_H, true)
         : PdfFontFactory.createFont(DEFAULT_PDF_TEXT_FONT, PdfEncodings.IDENTITY_H, true);
   }

   public static void writeTitle(Document doc, ExportTableLens tableLens) throws IOException {
      Paragraph p = new Paragraph(tableLens.getTableName());
      Font titleFont = tableLens.getTitleFont();

      p.setFont(getFont(true))
         .setFontSize(titleFont.getSize())
         .setBackgroundColor(convertColor(tableLens.getTitleBackground()));

      doc.add(p);
   }

   private static final Logger LOGGER = LoggerFactory.getLogger(PDFUtil.class);
}
