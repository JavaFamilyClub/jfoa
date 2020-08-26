package club.javafamily.runner.dao;

import javax.persistence.metamodel.Metamodel;
import java.io.Serializable;

/**
 * For exportable dao.
 */
public abstract class ExportableDao<T, R extends Serializable> extends BaseDao<T, R> {

   /**
    * get meta data.
    */
   public Metamodel getMetadata() {
      return getSession().getMetamodel();
   }

}
