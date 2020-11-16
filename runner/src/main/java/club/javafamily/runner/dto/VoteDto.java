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

package club.javafamily.runner.dto;

public class VoteDto {
   private int id;
   private boolean support;

   public VoteDto() {
   }

   public VoteDto(int id, boolean support) {
      this.id = id;
      this.support = support;
   }

   public boolean isSupport() {
      return support;
   }

   public void setSupport(boolean support) {
      this.support = support;
   }

   public int getId() {
      return id;
   }

   public void setId(int id) {
      this.id = id;
   }

   @Override
   public String toString() {
      return "VoteDto{" +
         "support=" + support +
         ", id=" + id +
         '}';
   }
}
