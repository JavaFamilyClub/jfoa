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

import club.javafamily.runner.util.Tool;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

import static club.javafamily.runner.util.Tool.DEFAULT_TIME_ZONE_STR;

public class SubjectRequestVO implements Serializable {
   private Integer id;
   private String subject;
   private String description;
   private String createUserName;
   @DateTimeFormat(pattern = Tool.DEFAULT_DATETIME_FORMAT)
   @JsonFormat(pattern=Tool.DEFAULT_DATETIME_FORMAT, timezone = DEFAULT_TIME_ZONE_STR)
   private Date createDate;

   private SubjectRequestVoteDto vote;

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

   public String getCreateUserName() {
      return createUserName;
   }

   public void setCreateUserName(String createUserName) {
      this.createUserName = createUserName;
   }

   public Date getCreateDate() {
      return createDate;
   }

   public void setCreateDate(Date createDate) {
      this.createDate = createDate;
   }

   public SubjectRequestVoteDto getVote() {
      return vote;
   }

   public void setVote(SubjectRequestVoteDto vote) {
      this.vote = vote;
   }

   @Override
   public String toString() {
      return "SubjectRequestVO{" +
         "id=" + id +
         ", subject='" + subject + '\'' +
         ", description='" + description + '\'' +
         ", createUserName='" + createUserName + '\'' +
         ", createDate=" + createDate +
         ", vote=" + vote +
         '}';
   }
}
