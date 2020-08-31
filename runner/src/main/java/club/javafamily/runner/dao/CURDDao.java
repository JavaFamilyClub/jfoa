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
