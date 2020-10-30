package club.javafamily.runner.util;

import club.javafamily.runner.common.table.lens.LensTool;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.stream.Collectors;

import static club.javafamily.runner.common.table.lens.LensTool.DEFAULT_HEADER_FONT;

public class LensToolTests {

   @Test
   public void testFont() throws IOException {
      PdfFontFactory.registerSystemDirectories();

//      System.out.println(PdfFontFactory.getRegisteredFonts().stream().collect(Collectors.joining("\n")));

      System.out.println(DEFAULT_HEADER_FONT.getFontName());
      System.out.println(DEFAULT_HEADER_FONT.getName());
      System.out.println(DEFAULT_HEADER_FONT.getPSName());
      System.out.println(DEFAULT_HEADER_FONT.getFamily());

      PdfFont font = PdfFontFactory.createRegisteredFont(DEFAULT_HEADER_FONT.getName());
      int width = font.getWidth("aaaaa");
      System.out.println(width);
   }

}
