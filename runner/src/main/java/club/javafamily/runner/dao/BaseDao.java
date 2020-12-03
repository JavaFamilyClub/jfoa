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

import club.javafamily.runner.common.filter.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.criteria.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public abstract class BaseDao<T, R extends Serializable> implements TableLensDao<T, R> {

   @Autowired
   private SessionFactory sessionFactory;

   public Session getSession() {
      if(sessionFactory != null) {
         return sessionFactory.getCurrentSession();
      }

      return null;
   }

   public abstract Class<T> getClazz();

   @Override
   public T get(R id) {
      return getSession().get(getClazz(), id);
   }

   @SuppressWarnings("all")
   @Override
   public <R extends Comparable<R>> List<T> getAll(DaoFilter<R> filter) {
      Session session = getSession();

      CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
      CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(getClazz());
      Root<T> root = criteriaQuery.from(getClazz());

      if(filter != null) {
         List<Predicate> conditions = new ArrayList<>();
         List<DaoFilterInfo<R>> filters = filter.filters();

         for(DaoFilterInfo filterInfo : filters) {
            if(filter.accept(filterInfo)) {
               conditions.add(buildCondition(criteriaBuilder, root, filterInfo));
            }
         }

         if(conditions.size() > 0) {
            criteriaQuery.where(criteriaBuilder.and(conditions.toArray(new Predicate[0])));
         }
      }

      List<T> list = session.createQuery(criteriaQuery).list();

      if(list == null) {
         list = new ArrayList<>();
      }

      return list;
   }

   @SuppressWarnings("all")
   private Predicate buildCondition(CriteriaBuilder criteriaBuilder,
                                    Root<T> root,
                                    DaoFilterInfo filterInfo)
   {
      String key = filterInfo.getKey();
      Path path = root.get(key);
      Comparable value = filterInfo.getValue();

      switch(filterInfo.getOperator()) {
         case GREATER_THAN_OR_EQUAL:
            return criteriaBuilder.greaterThanOrEqualTo(path, value);
         case GREATER_THAN:
            return criteriaBuilder.greaterThan(path, value);
         case LESS_THAN_OR_EQUAL:
            return criteriaBuilder.lessThanOrEqualTo(path, value);
         case LESS_THAN:
            return criteriaBuilder.lessThan(path, value);
         case NOT_EQUAL:
            return criteriaBuilder.notEqual(path, value);

         default:
            return criteriaBuilder.equal(path, value);
      }
   }

   @Override
   @SuppressWarnings("all")
   public R insert(T entity) {
      Session session = getSession();

      return (R) session.save(entity);
   }

   @Override
   public void update(T entity) {
      getSession().update(entity);
   }

   @Override
   public void delete(T entity) {
      getSession().delete(entity);
   }

   public void saveOrUpdate(T entity) {
      getSession().saveOrUpdate(entity);
   }
}
