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

import club.javafamily.runner.dao.RoleDao;
import club.javafamily.runner.domain.Role;
import club.javafamily.runner.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("roleService")
public class RoleServiceImpl implements RoleService {

   @Autowired
   public RoleServiceImpl(RoleDao roleDao) {
      this.roleDao = roleDao;
   }

   @Transactional(readOnly = true)
   @Override
   public List<Role> getRoles() {
      return roleDao.getAll();
   }

   @Transactional(readOnly = true)
   @Override
   public Role getRole(Integer id) {
      return roleDao.get(id);
   }

   @Transactional
   @Override
   public void deleteRole(Role role) {
      roleDao.delete(role);
   }

   @Transactional
   @Override
   public Integer addRole(Role role) {
      return roleDao.insert(role);
   }

   @Override
   public void updateRole(Role role) {
      roleDao.update(role);
   }

   private final RoleDao roleDao;
}
