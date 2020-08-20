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

package club.javafamily.runner.common.model.amqp;

import java.io.Serializable;
import java.util.Map;

public class TemplateEmailMessage implements Serializable {
   private String template;
   private String email;
   private String subject;
   private Map<String, Object> params;

   public TemplateEmailMessage() {
   }

   public TemplateEmailMessage(String template, String email,
                               String subject, Map<String, Object> params)
   {
      this.template = template;
      this.email = email;
      this.subject = subject;
      this.params = params;
   }

   public String getTemplate() {
      return template;
   }

   public void setTemplate(String template) {
      this.template = template;
   }

   public String getEmail() {
      return email;
   }

   public String getSubject() {
      return subject;
   }

   public void setSubject(String subject) {
      this.subject = subject;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   public Map<String, Object> getParams() {
      return params;
   }

   public void setParams(Map<String, Object> params) {
      this.params = params;
   }

   @Override
   public String toString() {
      return "TemplateEmailMessage{" +
         "template='" + template + '\'' +
         ", email='" + email + '\'' +
         ", subject='" + subject + '\'' +
         ", params=" + params +
         '}';
   }
}
