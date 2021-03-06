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

package club.javafamily.runner.web.portal.article.controller;

import club.javafamily.runner.domain.Article;
import club.javafamily.runner.service.ArticleService;
import club.javafamily.runner.service.CustomerService;
import club.javafamily.runner.util.SecurityUtil;
import club.javafamily.runner.web.portal.article.model.ArticleDto;
import club.javafamily.runner.web.portal.article.model.EditArticleModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(SecurityUtil.API_VERSION)
public class ArticleController {

   @PostMapping("/api/portal/article")
   public Integer writeArticle(@RequestBody EditArticleModel model) {
      Article article = model.convertToDomain();
      article.setCustomer(userService.getCurrentCustomer());

      return articleService.insert(article);
   }

   @GetMapping("/api/portal/article/{id}")
   public ArticleDto getArticle(@PathVariable("id") Integer id) {
      return articleService.getArticle(id);
   }

   @GetMapping("/api/portal/article/list/{offset}/{total}")
   public List<ArticleDto> getArticleList(@PathVariable("offset") Integer offset,
                                          @PathVariable("total") Integer total)
   {
      return articleService.getRangeArticle(offset, total);
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
