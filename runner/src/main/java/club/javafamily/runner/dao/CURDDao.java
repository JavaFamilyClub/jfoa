package club.javafamily.runner.dao;

import java.io.Serializable;
import java.util.List;

/**
 * CURD dao for simple crud operator
 * @param <T> entity
 * @param <R> id
 */
public interface CURDDao <T, R extends Serializable> {

   T get(R id);

   List<T> getAll();

   R insert(T entity);

   void update(T entity);

   void delete(T entity);
}
