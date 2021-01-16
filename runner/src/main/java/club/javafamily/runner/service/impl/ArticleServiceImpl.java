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

package club.javafamily.runner.service.impl;

import club.javafamily.runner.dao.ArticleDao;
import club.javafamily.runner.domain.Article;
import club.javafamily.runner.service.ArticleService;
import club.javafamily.runner.web.article.model.ArticleDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service("articleService")
public class ArticleServiceImpl implements ArticleService {

   @Autowired
   public ArticleServiceImpl(ArticleDao articleDao) {
      this.articleDao = articleDao;
   }

   @Transactional(readOnly = true)
   @Override
   public ArticleDto getArticle(Integer id) {
      Article article = get(id);

      return new ArticleDto(article);
   }

   @Transactional(readOnly = true)
   @Override
   public List<ArticleDto> getRangeArticle(int offset, int total) {
      List<Article> articles = getRange(offset, total);

      return articles.stream()
         .map(ArticleDto::new)
         .collect(Collectors.toList());
   }

   @Transactional(readOnly = true)
   @Override
   public Article get(Integer id) {
      return articleDao.get(id);
   }

   @Transactional
   @Override
   public void delete(Article obj) {
      articleDao.delete(obj);
   }

   @Transactional
   @Override
   public void update(Article obj) {
      articleDao.update(obj);
   }

   @Transactional
   @Override
   public Integer insert(Article obj) {
      return articleDao.insert(obj);
   }

   @Transactional(readOnly = true)
   @Override
   public List<Article> getRange(int offset, int total) {
      // TODO query by range
      return articleDao.getAll();
   }

   private final ArticleDao articleDao;
}
