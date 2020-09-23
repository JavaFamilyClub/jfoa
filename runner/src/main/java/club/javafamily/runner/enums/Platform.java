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

package club.javafamily.runner.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
public enum Platform {
   Mac("Mac", 0),
   Linux("Linux", 1),
   Win_x64("Win_x64", 2);

   Platform(String label, int value) {
      this.label = label;
      this.value = value;
   }

   public String getLabel() {
      return label;
   }

   public int getValue() {
      return value;
   }

   private String label;
   private int value;

   public static Platform parse(int platform) {
      switch(platform) {
         case 0:
            return Platform.Mac;
         case 1:
            return Platform.Linux;
         default:
            return Platform.Win_x64;
      }
   }
}
