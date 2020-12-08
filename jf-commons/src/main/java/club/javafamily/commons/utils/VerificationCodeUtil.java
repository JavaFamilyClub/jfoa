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

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public abstract class VerificationCodeUtil {

   public static final String DEFAULT_CODE_SESSION_KEY = "jf-verification-code";
   private static final int DEFAULT_CODE_WIDTH = 120;
   private static final int DEFAULT_CODE_HEIGHT = 30;
   private static final int DEFAULT_CODE_NUMBER_COUNT = 4;

   public static String createCode(HttpServletResponse response) throws IOException {
      return createCode(DEFAULT_CODE_WIDTH, DEFAULT_CODE_HEIGHT,
         DEFAULT_CODE_NUMBER_COUNT, response);
   }

   /**
    * Create verification code image.
    * @param width image width
    * @param height image height
    * @param response response, will setting no cache header
    * @return the code
    * @throws IOException write error
    */
   public static String createCode(int width, int height, int numberCount,
                                   HttpServletResponse response)
      throws IOException
   {
      // setting don't cache verification code image
      response.setDateHeader("Expires", -1);
      response.setHeader("Cache-Control", "no-cache");
      response.setHeader("Pragma", "no-cache");
      response.setContentType("image/jpeg");

      // 1. create code image
      BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
      // 2. getting graphics
      Graphics2D g = (Graphics2D) img.getGraphics();
      // 3. setting background and border
      g.setColor(new Color(199, 237, 204));
      g.fillRect(0, 0, width, height);
      g.setColor(Color. black);
      g.drawRect(0, 0, width - 1, height - 1);

      // 4. calc and draw interference line
      int lineNum = randNumber(3, 7);

      for( int i = 0; i < lineNum; i++){
         // random color
         g.setColor(new Color(randNumber(0, 255), randNumber(0, 255),
            randNumber(0, 255)));
         g.drawLine(randNumber(0, width), randNumber(0, height),
            randNumber(0, width), randNumber(0, height));
      }

      // 5. draw verification code
      int row = 10;
      int col = 22;
      // using default font
      g.setFont(new Font(null, Font.BOLD, 20));

      StringBuilder code = new StringBuilder();
      final int stepping = Math.round(width * 1.0F / numberCount);

      for( int i = 0; i < numberCount; i++){
         g.setColor( new Color(randNumber(0, 255), randNumber(0, 255),
            randNumber(0, 255)));

         // rotate code
         double theta = Math.toRadians(randNumber(-50, 50));
         g.rotate(theta, row, col);

         // draw code number
         int num = randNumber(0, 9);
         code.append(num);
         g.drawString(num + "", row, col);

         // restore angle
         g.rotate(0 - theta, row, col);

         // next
         row += stepping;
      }

      // store to session
//      request.getSession().setAttribute("valid", code.toString());
      // 6. write to response
      ServletOutputStream out = response.getOutputStream();
      ImageIO.write(img, "jpg", out);
      out.flush();

      return code.toString();
   }

   /**
    * create a random number in range
    * @param left left range
    * @param right right range
    * @return a random number in range
    */
   public static int randNumber(int left, int right) {
      return Tool.getSecureRandom().nextInt(right - left) + left;
   }

}
