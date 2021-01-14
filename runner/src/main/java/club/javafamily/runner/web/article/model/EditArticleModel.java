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

import club.javafamily.runner.domain.Article;
import club.javafamily.runner.domain.ArticleTag;

import java.util.Date;
import java.util.Set;

public class EditArticleModel {
   private String title;
   private String description;
   private Set<ArticleTag> tags;
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

   public Set<ArticleTag> getTags() {
      return tags;
   }

   public void setTags(Set<ArticleTag> tags) {
      this.tags = tags;
   }

   public String getContent() {
      return content;
   }

   public void setContent(String content) {
      this.content = content;
   }

   public Article convertToDomain() {
      Article article = new Article();

      article.setTitle(this.title);
      article.setDescription(this.description);
      article.setTags(this.tags);
      article.setContent(this.content);
      article.setCreateDate(new Date());

      return article;
   }
}
