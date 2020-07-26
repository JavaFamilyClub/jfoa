package club.javafamily.runner.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.criteria.CriteriaQuery;
import java.io.Serializable;
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

   @Override
   public List<T> getAll() {
      Session session = getSession();

      CriteriaQuery<T> query = session.getCriteriaBuilder().createQuery(getClazz());
      query.from(getClazz());

      return session.createQuery(query).list();
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
