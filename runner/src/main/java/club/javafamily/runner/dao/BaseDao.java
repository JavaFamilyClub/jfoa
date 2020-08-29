package club.javafamily.runner.dao;

import club.javafamily.runner.common.filter.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.criteria.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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

      return session.createQuery(criteriaQuery).list();
   }

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
