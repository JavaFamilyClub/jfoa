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

import club.javafamily.runner.domain.Customer;
import club.javafamily.runner.domain.SubjectRequest;

import java.io.Serializable;
import java.util.Date;

public class CreateSubjectModel implements Serializable {

   public String getSubject() {
      return subject;
   }

   public void setSubject(String subject) {
      this.subject = subject;
   }

   public String getDescription() {
      return description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public SubjectRequest convertToDomain(Customer customer) {
      SubjectRequest subjectRequest = new SubjectRequest();

      subjectRequest.setCustomer(customer);
      subjectRequest.setSubject(this.subject);
      subjectRequest.setDescription(this.description);
      subjectRequest.setCreateDate(new Date());

      return subjectRequest;
   }

   private String subject;
   private String description;
}
