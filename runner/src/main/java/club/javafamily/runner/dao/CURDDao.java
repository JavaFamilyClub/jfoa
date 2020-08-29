package club.javafamily.runner.dao;

import club.javafamily.runner.common.table.filter.Filter;

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

   List<T> getAll(Filter filter);

   R insert(T entity);

   void update(T entity);

   void delete(T entity);
}
