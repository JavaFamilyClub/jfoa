package club.javafamily.runner.common.table.lens;

import java.awt.*;

public class LensTool {

   /**
    * Default text font.
    */
   public static final Font DEFAULT_TEXT_FONT = new Font("Times New Roman", Font.PLAIN, 16);

   /**
    * Default header text font.
    */
   public static final Font DEFAULT_HEADER_FONT = new Font("Times New Roman", Font.BOLD, 18);

   public static final Color DEFAULT_TEXT_BG = Color.WHITE;

   public static final Color DEFAULT_HEADER_BG = Color.LIGHT_GRAY;

   public static final Font getDefaultHeaderFont() {
      return getFont(true);
   }

   public static final Font getDefaultTextFont() {
      return getFont(false);
   }

   private static final Font getFont(boolean bold) {
      Font font = bold ? DEFAULT_HEADER_FONT : DEFAULT_TEXT_FONT;

      return new Font(font.getName(),
         bold ? Font.BOLD : Font.PLAIN,
         font.getSize());
   }

}
