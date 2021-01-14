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

import club.javafamily.runner.dao.ArticleTagDao;
import club.javafamily.runner.domain.ArticleTag;
import club.javafamily.runner.service.ArticleTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("articleTagService")
public class ArticleTagServiceImpl implements ArticleTagService {

   @Autowired
   public ArticleTagServiceImpl(ArticleTagDao articleTagDao) {
      this.articleTagDao = articleTagDao;
   }

   @Transactional(readOnly = true)
   @Override
   public ArticleTag get(Integer id) {
      return articleTagDao.get(id);
   }

   @Transactional
   @Override
   public void delete(ArticleTag obj) {
      articleTagDao.delete(obj);
   }

   @Transactional
   @Override
   public void update(ArticleTag obj) {
      articleTagDao.update(obj);
   }

   @Transactional
   @Override
   public Integer insert(ArticleTag obj) {
      return articleTagDao.insert(obj);
   }

   @Transactional(readOnly = true)
   @Override
   public List<ArticleTag> getRange(int offset, int total) {
      //TODO query by range
      return articleTagDao.getAll();
   }

   private final ArticleTagDao articleTagDao;
}
