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

import club.javafamily.runner.annotation.Audit;
import club.javafamily.runner.annotation.AuditObject;
import club.javafamily.runner.common.MessageException;
import club.javafamily.runner.dao.RoleDao;
import club.javafamily.runner.domain.Log;
import club.javafamily.runner.domain.Role;
import club.javafamily.commons.enums.ActionType;
import club.javafamily.runner.enums.ResourceEnum;
import club.javafamily.runner.service.*;
import club.javafamily.runner.util.I18nUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service("roleService")
public class RoleServiceImpl implements RoleService {

   @Autowired
   public RoleServiceImpl(UserHandler userHandler,
                          LogService logService,
                          RoleDao roleDao)
   {
      this.userHandler = userHandler;
      this.logService = logService;
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
   public Role getRoleByName(String name) {
      return roleDao.getByName(name);
   }

   @Audit(
      value = ResourceEnum.Role,
      actionType = ActionType.DELETE
   )
   @Transactional
   @Override
   public void deleteRole(@AuditObject("getName()") Role role) {
      roleDao.delete(role);
   }

   @Transactional
   @Override
   public void deleteRoles(Integer[] ids) {
      if(ArrayUtils.isEmpty(ids)) {
         return;
      }

      Role role;

      for(Integer id : ids) {
         role = roleDao.get(id);

         if(role == null) {
            throw new MessageException(I18nUtil.getString("em.role.idRoleNotExist", id));
         }

         roleDao.delete(role);

         Log log = new Log();
         log.setResource(ResourceEnum.Role.getLabel() + ":" + role.getName());
         log.setAction(ActionType.DELETE.getLabel());
         log.setCustomer(userHandler.getAuditUser());
         log.setDate(new Date());
         logService.insertLog(log);
      }
   }

   @Audit(
      value = ResourceEnum.Role
   )
   @Transactional
   @Override
   public Integer addRole(@AuditObject("getName()") Role role) {
      return roleDao.insert(role);
   }

   @Audit(
      value = ResourceEnum.Role,
      actionType = ActionType.MODIFY
   )
   @Transactional
   @Override
   public void updateRole(@AuditObject("getName()") Role role) {
      roleDao.update(role);
   }

   private final UserHandler userHandler;
   private final LogService logService;
   private final RoleDao roleDao;
}
