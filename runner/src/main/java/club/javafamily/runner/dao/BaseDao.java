package club.javafamily.runner.dao;

import club.javafamily.runner.common.table.filter.Filter;
import club.javafamily.runner.common.table.filter.FilterInfo;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.criteria.*;
import java.io.Serializable;
import java.util.*;

public abstract class BaseDao<T, R extends Serializable> implements CURDDao<T, R> {

   @Autowired
   private SessionFactory sessionFactory;

   public Session getSession() {
      if(sessionFactory != null) {
         return sessionFactory.getCurrentSession();
      }

      return null;
   }

   protected abstract Class<T> getClazz();

   @Override
   public T get(R id) {
      return getSession().get(getClazz(), id);
   }

   @Override
   public List<T> getAll(Filter filter) {
      Session session = getSession();

      CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
      CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(getClazz());
      Root<T> root = criteriaQuery.from(getClazz());

      if(filter != null) {
         List<Predicate> conditions = new ArrayList<>();
         List<FilterInfo> filters = filter.filters();

         for(FilterInfo filterInfo : filters) {
            String key = filterInfo.getKey();
            Object value = filterInfo.getValue();

            if(filter.accept(filterInfo)) {
//               conditions.add(criteriaBuilder.greaterThanOrEqualTo(root.get(key), value));
            }
         }

         if(conditions.size() > 0) {
            criteriaQuery.where(criteriaBuilder.and(conditions.toArray(new Predicate[0])));
         }
      }

      return session.createQuery(criteriaQuery).list();
   }

   @Override
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
}
