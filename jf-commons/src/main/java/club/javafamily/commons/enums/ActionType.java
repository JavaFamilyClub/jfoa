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

package club.javafamily.commons.enums;

public enum ActionType {
   ADD(1, "Add"),
   DELETE(2, "Delete"),
   MODIFY(3, "Modify"),
   Login(4, "Login"),
   Achieve(5, "Achieve"),
   Upload(6, "Upload"),
   Authorization(7, "authorization")
   ;

   private int type;
   private String label;

   ActionType(int type, String label) {
      this.type = type;
      this.label = label;
   }

   public int getType() {
      return type;
   }

   public String getLabel() {
    return label;
  }
}
