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
import java.io.IOException;

public final class PDFUtil {

   private PDFUtil() {
   }

   private static String DEFAULT_PDF_TEXT_FONT;
   private static String DEFAULT_PDF_BOLD_FONT;

   static {
      try {
//         PdfFontFactory.registerSystemDirectories();
         DEFAULT_PDF_TEXT_FONT = ResourceUtils.getURL(
            ResourceUtils.CLASSPATH_URL_PREFIX + "public/fonts/simsun.ttf").getPath();

         DEFAULT_PDF_BOLD_FONT = ResourceUtils.getURL(
            ResourceUtils.CLASSPATH_URL_PREFIX + "public/fonts/simhei.TTF").getPath();
      }
      catch(Exception ignore) {
         ignore.printStackTrace();
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

      return font.isBold()
         ? PdfFontFactory.createFont(DEFAULT_PDF_TEXT_FONT, PdfEncodings.IDENTITY_H, true)
         : PdfFontFactory.createFont(DEFAULT_PDF_BOLD_FONT, PdfEncodings.IDENTITY_H, true);
   }

   private static final Logger LOGGER = LoggerFactory.getLogger(PDFUtil.class);
}
