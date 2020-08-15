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

package club.javafamily.runner.web.portal.model;

public class MailAuthorModel {
   private String subject;
   private String content;
   private String cc;

   public String getSubject() {
      return subject;
   }

   public void setSubject(String subject) {
      this.subject = subject;
   }

   public String getContent() {
      return content;
   }

   public void setContent(String content) {
      this.content = content;
   }

   public String getCc() {
      return cc;
   }

   public void setCc(String cc) {
      this.cc = cc;
   }

   @Override
   public String toString() {
      return "MailAuthorModel{" +
         "content='" + content + '\'' +
         ", cc='" + cc + '\'' +
         '}';
   }
}
