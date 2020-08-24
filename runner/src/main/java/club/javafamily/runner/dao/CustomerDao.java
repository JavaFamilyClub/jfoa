/*
 * Copyright (c) 2019, JavaFamily Technology Corp, All Rights Reserved.
 *
 * The software and information contained herein are copyrighted and
 * proprietary to AngBoot Technology Corp. This software is furnished
 * pursuant to a written license agreement and may be used, copied,
 * transmitted, and stored only in accordance with the terms of such
 * license and with the inclusion of the above copyright notice. Please
 * refer to the file "COPYRIGHT" for further copyright and licensing
 * information. This software and information or any other copies
 * thereof may not be provided or otherwise made available to any other
 * person.
 */

package club.javafamily.runner.dao;

import club.javafamily.runner.domain.Customer;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.*;

@Repository
public class CustomerDao extends BaseDao<Customer, Integer> {

   @Override
   protected Class<Customer> getClazz() {
      return Customer.class;
   }

   public Customer getUserByAccount(String account) {
      Session session = getSession();

      CriteriaBuilder cb = session.getCriteriaBuilder();
      CriteriaQuery<Customer> query = cb.createQuery(getClazz());
      Root<Customer> root = query.from(getClazz());
      query.where(cb.equal(root.get("account"), account));

      return session.createQuery(query).uniqueResultOptional().orElse(null);
   }

}
