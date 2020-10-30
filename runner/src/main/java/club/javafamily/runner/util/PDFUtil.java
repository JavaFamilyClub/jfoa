package club.javafamily.runner.util;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.io.IOException;

public final class PDFUtil {

   private PDFUtil() {
      try {
         PdfFontFactory.registerSystemDirectories();
         DEFAULT_PDF_TEXT_FONT = PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);
         DEFAULT_PDF_BOLD_FONT = PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);
      }
      catch(Exception ignore) {
      }
   }

   private static PdfFont DEFAULT_PDF_TEXT_FONT;
   private static PdfFont DEFAULT_PDF_BOLD_FONT;

   public static Color convertColor(java.awt.Color color) {
      return new DeviceRgb(color);
   }

   public static PdfFont convertFont(Font font) {
      try {
         PdfFont pdfFont = PdfFontFactory.createRegisteredFont(font.getName());

         return pdfFont;
      } catch(IOException e) {
         LOGGER.warn("Registered font is not found! {}", font.getFontName());
      }

      return font.isBold() ? DEFAULT_PDF_BOLD_FONT : DEFAULT_PDF_TEXT_FONT;
   }

   private static final Logger LOGGER = LoggerFactory.getLogger(PDFUtil.class);
}
