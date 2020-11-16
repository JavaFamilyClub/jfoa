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

@Entity(name = "t_subject_request_vote")
public class SubjectRequestVote implements Serializable {
   @Id
   @GeneratedValue(generator = "pkGenerator")
   @GenericGenerator(
      name = "pkGenerator",
      strategy = "foreign",
      parameters = {
         @org.hibernate.annotations.Parameter(name = "property", value = "subjectRequest")
      }
   )
   @Column(nullable = false, unique = true)
   private Integer id;

   private Integer support;

   private Integer oppose;

   @OneToOne(fetch = FetchType.LAZY)
   @PrimaryKeyJoinColumn
   private SubjectRequest subjectRequest;

   public SubjectRequestVote() {
   }

   public SubjectRequestVote(SubjectRequest subjectRequest) {
      this.subjectRequest = subjectRequest;
   }

   public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
   }

   public Integer getSupport() {
      return support;
   }

   public void setSupport(Integer support) {
      this.support = support;
   }

   public Integer getOppose() {
      return oppose;
   }

   public void setOppose(Integer against) {
      this.oppose = against;
   }

   public SubjectRequest getSubjectRequest() {
      return subjectRequest;
   }

   public void setSubjectRequest(SubjectRequest subjectRequest) {
      this.subjectRequest = subjectRequest;
   }

   @Override
   public String toString() {
      return "SubjectRequestVote{" +
         "id=" + id +
         ", support=" + support +
         ", oppose=" + oppose +
         '}';
   }
}
