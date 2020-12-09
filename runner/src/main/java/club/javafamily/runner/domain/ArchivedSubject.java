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

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity(name = "t_archived_sr")
public class ArchivedSubject implements Serializable {
   @Id
   @GeneratedValue(generator = "pkGenerator")
   @GenericGenerator(
      name = "pkGenerator",
      strategy = "foreign",
      parameters = {
         @org.hibernate.annotations.Parameter(name = "property", value = "subjectRequest")
      }
   )
   private Integer id;

   private String url;

   @OneToOne(fetch = FetchType.LAZY)
   @PrimaryKeyJoinColumn
   private SubjectRequest subjectRequest;

   @Temporal(TemporalType.TIMESTAMP)
   private Date date;

   public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
   }

   public String getUrl() {
      return url;
   }

   public void setUrl(String url) {
      this.url = url;
   }

   public SubjectRequest getSubjectRequest() {
      return subjectRequest;
   }

   public void setSubjectRequest(SubjectRequest subjectRequest) {
      this.subjectRequest = subjectRequest;
   }

   public Date getDate() {
      return date;
   }

   public void setDate(Date date) {
      this.date = date;
   }
}
