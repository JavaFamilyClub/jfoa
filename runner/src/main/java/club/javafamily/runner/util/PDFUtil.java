package club.javafamily.runner.util;

import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;

import java.awt.*;

public final class PDFUtil {

   private PDFUtil() {
   }

   private static PdfFont DEFAULT_PDF_TEXT_FONT;
   private static PdfFont DEFAULT_PDF_BOLD_FONT;

   static {
      try {
//         PdfFontFactory.registerSystemDirectories();

         String simsunPath = ResourceUtils.getURL(ResourceUtils.CLASSPATH_URL_PREFIX + "public/fonts/simsun.ttf").getPath();
         String simsunBoldPath = ResourceUtils.getURL(ResourceUtils.CLASSPATH_URL_PREFIX + "public/fonts/simhei.TTF").getPath();

         DEFAULT_PDF_TEXT_FONT = PdfFontFactory.createFont(simsunPath, PdfEncodings.IDENTITY_H, true);
         DEFAULT_PDF_BOLD_FONT = PdfFontFactory.createFont(simsunBoldPath, PdfEncodings.IDENTITY_H, true);
      }
      catch(Exception ignore) {
         ignore.printStackTrace();
      }
   }

   public static Color convertColor(java.awt.Color color) {
      return new DeviceRgb(color);
   }

   public static PdfFont convertFont(Font font) {
//      try {
//         PdfFont pdfFont = PdfFontFactory.createRegisteredFont(font.getName());
//
//         return pdfFont;
//      } catch(IOException e) {
//         LOGGER.warn("Registered font is not found! {}", font.getFontName());
//      }

      return font.isBold() ? DEFAULT_PDF_BOLD_FONT : DEFAULT_PDF_TEXT_FONT;
   }

   private static final Logger LOGGER = LoggerFactory.getLogger(PDFUtil.class);
}
