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

package club.javafamily.runner.service.impl;

import club.javafamily.runner.common.filter.DaoFilter;
import club.javafamily.runner.common.filter.EqualsFilterInfo;
import club.javafamily.runner.dao.PermissionDao;
import club.javafamily.runner.domain.Permission;
import club.javafamily.runner.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service("permissionService")
public class PermissionServiceImpl implements PermissionService {

   @Transactional(readOnly = true)
   @Override
   public List<Permission> getPermissionsByResource(int id) {
      DaoFilter<Integer> filter
         = PermissionServiceImpl.getPermissionByResourceFilter(id);

      return permissionDao.getAll(filter);
   }

   @Transactional
   @Override
   public Integer insert(Permission permission) {
      return permissionDao.insert(permission);
   }

   public static  <T extends Comparable<T>> DaoFilter<T> getPermissionByResourceFilter(T value) {
      EqualsFilterInfo<T> filterInfo = new EqualsFilterInfo<>("resource");
      filterInfo.setValue(value);

      return new DaoFilter<>(Collections.singletonList(filterInfo));
   }

   @Autowired
   public PermissionServiceImpl(PermissionDao permissionDao) {
      this.permissionDao = permissionDao;
   }

   private final PermissionDao permissionDao;
}
