package club.javafamily.runner.util;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbookFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static club.javafamily.runner.common.table.lens.LensTool.DEFAULT_HEADER_FONT;
import static club.javafamily.runner.common.table.lens.LensTool.DEFAULT_TEXT_FONT;

public class LensToolTests {

   @Test
   public void printSystemFonts() throws IOException {
      PdfFontFactory.registerSystemDirectories();
      String fonts = String.join("\n", PdfFontFactory.getRegisteredFonts());
      System.out.println(fonts);
      LOGGER.warn("System Fonts: \n {}", fonts);
   }

   @Test
   public void testITextFont() throws IOException {
      PdfFontFactory.registerSystemDirectories();

      System.out.println(DEFAULT_HEADER_FONT.getFontName());
      System.out.println(DEFAULT_HEADER_FONT.getName());
      System.out.println(DEFAULT_HEADER_FONT.getPSName());
      System.out.println(DEFAULT_HEADER_FONT.getFamily());

      PdfFont font = PdfFontFactory.createRegisteredFont(DEFAULT_HEADER_FONT.getName());
      Assertions.assertNotNull(font, "default header font can't convert to pdfFont");

      font = PdfFontFactory.createRegisteredFont(DEFAULT_TEXT_FONT.getName());
      Assertions.assertNotNull(font, "default text font can't convert to pdfFont");

      int width = font.getWidth("jfoa");
      System.out.println(width);
   }

   @Test
   public void testPOIFont() {
     Workbook workbook = XSSFWorkbookFactory.createWorkbook();
      Font font = workbook.createFont();
//      font.setBold(true);
      font.setFontName(DEFAULT_HEADER_FONT.getName());

      System.out.println(DEFAULT_HEADER_FONT.isBold());
      System.out.println(font.getFontName());
      System.out.println(font.getBold());
   }

   private static final Logger LOGGER = LoggerFactory.getLogger(LensToolTests.class);
}
