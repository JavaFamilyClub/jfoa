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

package club.javafamily.runner.web.article.controller;

import club.javafamily.runner.domain.Article;
import club.javafamily.runner.service.ArticleService;
import club.javafamily.runner.service.CustomerService;
import club.javafamily.runner.util.SecurityUtil;
import club.javafamily.runner.web.article.model.EditArticleModel;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(SecurityUtil.API_VERSION)
public class ArticleController {

   @RequiresUser
   @PostMapping("/portal/article")
   public Integer writeArticle(EditArticleModel model) {
      Article article = model.convertToDomain();
      article.setCustomer(userService.getCurrentCustomer());

      return articleService.insert(article);
   }

   @GetMapping("/portal/article/list/{offset}/{total}")
   public List<Article> getArticleList(@PathVariable("offset") int offset,
                                       @PathVariable("total") int total)
   {
      return articleService.getRange(offset, total);
   }

   @Autowired
   public ArticleController(CustomerService userService,
                            ArticleService articleService)
   {
      this.userService = userService;
      this.articleService = articleService;
   }

   private final CustomerService userService;
   private final ArticleService articleService;
}
