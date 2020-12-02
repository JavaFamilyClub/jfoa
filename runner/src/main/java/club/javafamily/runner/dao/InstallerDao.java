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

import club.javafamily.runner.domain.Installer;
import club.javafamily.commons.enums.Platform;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class InstallerDao extends BaseDao<Installer, Integer>  {
   @Override
   public Class<Installer> getClazz() {
      return Installer.class;
   }

   public Installer getInstaller(Platform platform, String version) {
      Session session = getSession();

      CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
      CriteriaQuery<Installer> criteriaQuery = criteriaBuilder.createQuery(getClazz());
      Root<Installer> root = criteriaQuery.from(getClazz());
      List<Predicate> conditions = new ArrayList<>();

      Path path = root.get("platform");
      conditions.add(criteriaBuilder.equal(path, platform));

      path = root.get("version");
      conditions.add(criteriaBuilder.equal(path, version));

      criteriaQuery.where(criteriaBuilder.and(conditions.toArray(new Predicate[0])));

      return  session.createQuery(criteriaQuery).uniqueResult();
   }
}
