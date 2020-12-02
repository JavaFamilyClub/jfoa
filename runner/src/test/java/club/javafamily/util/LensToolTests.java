package club.javafamily.util;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbookFactory;
import org.junit.jupiter.api.*;
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
      LOGGER.warn("System Fonts: \n\n {} \n\n", fonts);
   }

   @Test
   public void printDefaultFont() {
      java.awt.Font font = new java.awt.Font(null, java.awt.Font.PLAIN, 16);
      LOGGER.warn("Default font name: {} \n\n ", font.getName());
   }

   @Disabled("Export PDF using simsun font.")
   @Test
   public void testITextFont() throws IOException {
      PdfFontFactory.registerSystemDirectories();

      PdfFont font = PdfFontFactory.createRegisteredFont(DEFAULT_HEADER_FONT.getName());
      Assertions.assertNotNull(font, "default header font can't convert to pdfFont");

      font = PdfFontFactory.createRegisteredFont(DEFAULT_TEXT_FONT.getName());
      Assertions.assertNotNull(font, "default text font can't convert to pdfFont");
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
