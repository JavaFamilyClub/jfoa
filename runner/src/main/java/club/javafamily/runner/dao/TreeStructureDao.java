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

import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.criteria.*;
import java.io.Serializable;
import java.util.List;

public abstract class TreeStructureDao<T, R extends Serializable> extends BaseDao<T, R> {

   public List<T> getTop() {
      Session session = getSession();
      CriteriaBuilder builder = session.getCriteriaBuilder();
      CriteriaQuery<T> query = builder.createQuery(getClazz());
      Root<T> root = query.from(getClazz());
      query.select(root);

      query.where(builder.isNull(root.get("parent")));

      Query<T> resultQuery = session.createQuery(query);

      return resultQuery != null ? resultQuery.getResultList() : null;
   }
}
