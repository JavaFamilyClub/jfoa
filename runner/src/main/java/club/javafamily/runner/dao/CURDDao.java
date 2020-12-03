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

package club.javafamily.runner.dao;

import club.javafamily.runner.common.filter.DaoFilter;

import java.io.Serializable;
import java.util.List;

/**
 * CURD dao for simple crud operator
 * @param <T> entity
 * @param <R> id
 */
public interface CURDDao <T, R extends Serializable> {

   T get(R id);

   default List<T> getAll() {
      return this.getAll(null);
   }

   <M extends Comparable<M>> List<T> getAll(DaoFilter<M> filter);

   R insert(T entity);

   void update(T entity);

   void delete(T entity);
}
