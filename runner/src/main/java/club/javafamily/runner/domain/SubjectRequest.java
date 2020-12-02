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

package club.javafamily.runner.domain;

import club.javafamily.commons.annotation.TableLensColumn;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity(name = "t_subject_request")
public class SubjectRequest implements Serializable {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer id;

   private String subject;
   private String description;

   @TableLensColumn("getName()")
   @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
   @ManyToOne(targetEntity = Customer.class, fetch = FetchType.EAGER)
   private Customer customer;

   private Date createDate;

   public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
   }

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

   public Customer getCustomer() {
      return customer;
   }

   public void setCustomer(Customer customer) {
      this.customer = customer;
   }

   public Date getCreateDate() {
      return createDate;
   }

   public void setCreateDate(Date createDate) {
      this.createDate = createDate;
   }

   @Override
   public String toString() {
      return "SubjectRequest{" +
         "id=" + id +
         ", subject='" + subject + '\'' +
         ", description='" + description + '\'' +
         ", createDate=" + createDate +
         '}';
   }
}
