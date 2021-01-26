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

package club.javafamily.runner.web.portal.article.model;

import club.javafamily.runner.domain.Article;
import club.javafamily.runner.domain.ArticleTag;
import club.javafamily.runner.enums.ArticleType;

import java.util.*;

public class EditArticleModel {
   private String title;
   private ArticleType type;
   private String description;
   private List<ArticleTag> tags;
   private String content;

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

   public String getContent() {
      return content;
   }

   public void setContent(String content) {
      this.content = content;
   }

   public ArticleType getType() {
      return type;
   }

   public void setType(ArticleType type) {
      this.type = type;
   }

   public Article convertToDomain() {
      Article article = new Article();

      article.setType(this.type);
      article.setTitle(this.title);
      article.setDescription(this.description);
      article.setTags(this.tags);
      article.setContent(this.content);
      article.setCreateDate(new Date());

      return article;
   }
}
