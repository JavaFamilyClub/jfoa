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

package club.javafamily.runner.web.article.model;

import club.javafamily.commons.utils.Tool;
import club.javafamily.runner.domain.Article;
import club.javafamily.runner.domain.ArticleTag;
import club.javafamily.runner.enums.ArticleType;
import club.javafamily.runner.util.SecurityUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

public class ArticleItem {
   private Integer id;
   private ArticleType type;
   private String title;
   private String description;
   private List<ArticleTag> tags;
   private String user;
   @DateTimeFormat(pattern = Tool.DEFAULT_DATETIME_FORMAT)
   @JsonFormat(pattern=Tool.DEFAULT_DATETIME_FORMAT, timezone = Tool.DEFAULT_TIME_ZONE_STR)
   private Date createDate;

   public ArticleItem(Article article) {
      this.id = article.getId();
      this.title = article.getTitle();
      this.description = article.getDescription();
      this.createDate = article.getCreateDate();
      this.tags = article.getTags();
      this.user = article.getCustomer() != null
         ? article.getCustomer().getName()
         : SecurityUtil.Anonymous;
      this.type = article.getType();
   }

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

   public String getUser() {
      return user;
   }

   public void setUser(String user) {
      this.user = user;
   }

   public Date getCreateDate() {
      return createDate;
   }

   public void setCreateDate(Date createDate) {
      this.createDate = createDate;
   }
}
