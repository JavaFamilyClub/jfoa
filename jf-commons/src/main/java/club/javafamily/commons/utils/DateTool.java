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

public class DateTool {

   private static final int DAY = 1000 * 60 * 60 * 24;
   private static final int HOUR = 1000 * 60 * 60;
   private static final int MINUTE = 1000 * 60;
   private static final int SECONDS = 1000;

   /**
    * Parse timestamp to day-hour-minus-second
    * @param timestamp long timestamp
    * @return time part array
    *    result[0]: day
    *    result[1]: hour
    *    result[2]: minus
    *    result[3]: second
    *
    */
   public static int[] parseTime(long timestamp) {
      int[] result = new int[4];

      result[0] = (int) (1.0F * timestamp / DAY);
      result[1] = (int) (1.0F * (timestamp % DAY) / HOUR);
      result[2] = (int) (1.0F * (timestamp % HOUR) / MINUTE);
      result[3] = (int) (1.0F * (timestamp % MINUTE) / SECONDS);

      return result;
   }
}
