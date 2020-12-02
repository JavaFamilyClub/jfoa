package club.javafamily.runner.util;

import club.javafamily.commons.utils.Tool;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;

import java.io.File;

public class PDFUtilTests {

   @Disabled("Write file")
   @Test
   public void testExportPDFUsingSimsunFont() throws Exception {
//      String fontPath = ResourceUtils.getURL(ResourceUtils.CLASSPATH_URL_PREFIX + "public/fonts/simhei.TTF").getPath();
      String fontPath = ResourceUtils.getURL(ResourceUtils.CLASSPATH_URL_PREFIX + "public/fonts/simsun.ttf").getPath();
      File file = new File(fontPath);

      LOGGER.info("font path is {}", fontPath);

      Assertions.assertTrue(file.exists(), "Font file is not exists.");

//      PdfFontFactory.register(fontPath, "jf-simsunBold");
//      PdfFont font = PdfFontFactory.createRegisteredFont("jf-simsunBold");
      PdfFont font = PdfFontFactory.createFont(fontPath, PdfEncodings.IDENTITY_H, true);

      Assertions.assertNotNull(font, "Can't create simsun-bold font for export pdf.");

      // write pdf
      String parent = Tool.getClasspath();
      LOGGER.info("dest dir: {}", parent);

      File dest = new File(parent + "/test.pdf");

      if(!dest.exists()) {
         dest.createNewFile();
         LOGGER.info("Create file: {}", dest.getAbsolutePath());
      }

      PdfWriter writer = new PdfWriter(dest);
      PdfDocument pdf = new PdfDocument(writer);
      Document document = new Document(pdf);
      document.add(new Paragraph("中文!").setFont(font));
      document.close();
   }

   private static final Logger LOGGER = LoggerFactory.getLogger(PDFUtilTests.class);
}
