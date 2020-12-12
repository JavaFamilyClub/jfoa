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

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
public enum PermissionEnum {
   READ(1),
   WRITE(2),
   DELETE(4),
   ACCESS(8),
   ADMIN(16);

   private int permission;

   PermissionEnum(int permission) {
      this.permission = permission;
   }

   public int getPermission() {
      return this.permission;
   }

   public static PermissionEnum valueOf(int permission) {
      switch(permission) {
         case 1:
            return READ;
         case 2:
            return WRITE;
         case 4:
            return DELETE;
         case 8:
            return ACCESS;
         case 16:
            return ADMIN;
         default:
            throw new RuntimeException("Permission is not found! " + permission);
      }
   }
}
