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

package club.javafamily.commons.utils;

import club.javafamily.commons.lens.ExportTableLens;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.io.IOException;

public final class PDFUtil {

   private PDFUtil() {
   }

   private static byte[] defaultTextFontData;
   private static byte[] defaultBoldFontData;

   // by JackLi: font byte can't reuse.
   // multi-thread export check <code>document.close()</code> will throw check exception
   private static final ThreadLocal<byte[]> DEFAULT_PDF_TEXT_FONT
      = ThreadLocal.withInitial(() -> defaultTextFontData);
   private static final ThreadLocal<byte[]> DEFAULT_PDF_BOLD_FONT
      = ThreadLocal.withInitial(() -> defaultBoldFontData);

   static {
      initDefaultFontData();
   }

   private static void initDefaultFontData() {
      try {
//         PdfFontFactory.registerSystemDirectories();
         defaultTextFontData = Tool.getConfigFileData("public/fonts/simsun.ttf");
         defaultBoldFontData = Tool.getConfigFileData("public/fonts/simhei.TTF");
      }
      catch(Exception e) {
         e.printStackTrace();
      }
   }

   private static void resetDefaultFont() {
      try {
         DEFAULT_PDF_TEXT_FONT.set(defaultTextFontData);
         DEFAULT_PDF_BOLD_FONT.set(defaultBoldFontData);
      }
      catch(Exception e) {
         e.printStackTrace();
      }
   }

   /**
    * Convert awt's color to iText's color.
    * @param color awt's color.
    * @return iText's color
    */
   public static Color convertColor(java.awt.Color color) {
      return new DeviceRgb(color);
   }

   /**
    * Convert awt's font to iText's font.
    * @param font awt's font.
    * @return iText's font
    */
   public static PdfFont convertFont(Font font) throws IOException {
      // 这在项目打成 jar 包后会出现访问不到字体文件的错误.
//      try {
//         PdfFont pdfFont = PdfFontFactory.createRegisteredFont(font.getName());
//
//         return pdfFont;
//      } catch(IOException e) {
//         LOGGER.warn("Registered font is not found! {}", font.getFontName());
//      }

      ThreadLocal<byte[]> fontProvider = font.isBold()
         ? DEFAULT_PDF_BOLD_FONT
         : DEFAULT_PDF_TEXT_FONT;

      byte[] fontData = fontProvider.get();

      if(fontData == null) {
         resetDefaultFont();
         fontData = fontProvider.get();
      }

      return PdfFontFactory.createFont(fontData, PdfEncodings.IDENTITY_H, true);
   }

   public static void writeTitle(Document doc, ExportTableLens tableLens) throws IOException {
      Paragraph p = new Paragraph(tableLens.getTableName());
      Font titleFont = tableLens.getTitleFont();

      p.setFont(convertFont(titleFont))
         .setFontSize(titleFont.getSize())
         .setTextAlignment(TextAlignment.CENTER)
         .setHorizontalAlignment(HorizontalAlignment.CENTER)
         .setFontColor(convertColor(tableLens.getFontColor()));

      doc.add(p);
   }

   private static final Logger LOGGER = LoggerFactory.getLogger(PDFUtil.class);
}
