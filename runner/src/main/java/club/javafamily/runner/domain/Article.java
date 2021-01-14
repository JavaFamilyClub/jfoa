/*
 * Copyright (c) 2021, JavaFamily Technology Corp, All Rights Reserved.
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

import club.javafamily.commons.utils.Tool;
import club.javafamily.runner.enums.ArticleType;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.Cascade;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity(name = "t_article")
public class Article implements Serializable {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer id;

   private ArticleType type;

   private String title;

   private String description;

   @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
   @ManyToMany(targetEntity = ArticleTag.class, fetch = FetchType.EAGER)
   private List<ArticleTag> tags;

   @Column(columnDefinition = "CLOB", length = 32766)
   private String content;

   @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
   @ManyToOne(targetEntity = Customer.class)
   private Customer customer;

   @DateTimeFormat(pattern = Tool.DEFAULT_DATETIME_FORMAT)
   @JsonFormat(pattern=Tool.DEFAULT_DATETIME_FORMAT, timezone = Tool.DEFAULT_TIME_ZONE_STR)
   @Temporal(TemporalType.TIMESTAMP)
   private Date createDate;

   public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
   }

   public ArticleType getType() {
      return type;
   }

   public void setType(ArticleType type) {
      this.type = type;
   }

   public String getTitle() {
      return title;
   }

   public void setTitle(String title) {
      this.title = title;
   }

   public String getDescription() {
      return description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public List<ArticleTag> getTags() {
      return tags;
   }

   public void setTags(List<ArticleTag> tags) {
      this.tags = tags;
   }

   @Column(columnDefinition = "CLOB", length = 65532)
   public String getContent() {
      return content;
   }

   public void setContent(String content) {
      this.content = content;
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

}
