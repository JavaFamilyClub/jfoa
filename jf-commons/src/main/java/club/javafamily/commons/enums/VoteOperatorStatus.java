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

public enum VoteOperatorStatus {
   /**
    * allow operator
    */
   Allow(1),

   /**
    * invalid or conflict
    */
   Conflict(0),

   /**
    * operator again in same resource
    */
   Again(-1);

   VoteOperatorStatus(int value) {
      this.value = value;
   }

   public int getValue() {
      return value;
   }

   private int value;
}
