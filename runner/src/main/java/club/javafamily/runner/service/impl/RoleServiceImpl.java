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

import club.javafamily.commons.enums.*;
import club.javafamily.runner.annotation.Audit;
import club.javafamily.runner.annotation.AuditObject;
import club.javafamily.runner.common.MessageException;
import club.javafamily.runner.dao.RoleDao;
import club.javafamily.runner.domain.*;
import club.javafamily.runner.enums.ResourceEnum;
import club.javafamily.runner.service.*;
import club.javafamily.runner.util.*;
import club.javafamily.runner.web.em.model.ResourceItemSettingModel;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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
   public List<ResourceItemSettingModel> getRolesByResource(Integer resourceId) {
      ResourceEnum resource = ResourceEnum.parse(resourceId);

      if(resource == null || resource == ResourceEnum.Unknown) {
         LOGGER.info("Resource is not found: {}", resourceId);
         return null;
      }

      List<Role> roles = getRoles();
      Map<Integer, ResourceItemSettingModel> map = new HashMap<>(); // role id

      for(Role role : roles) {
         for(Permission permission : role.getPermissions()) {
            if(!Objects.equals(permission.getResource(), resourceId)) {
               continue;
            }

            EnumSet<PermissionEnum> permissionEnums
               = SecurityUtil.parsePermissionOperator(permission.getOperator());

            map.computeIfAbsent(role.getId(), (k) -> {
               ResourceItemSettingModel item = new ResourceItemSettingModel();

               item.setId(permission.getId());
               item.setRoleId(role.getId());
               item.setName(role.getName());

               item.setType(ResourceSettingType.Role);
               item.setRead(permissionEnums.contains(PermissionEnum.READ));
               item.setWrite(permissionEnums.contains(PermissionEnum.WRITE));
               item.setDelete(permissionEnums.contains(PermissionEnum.DELETE));
               item.setAccess(permissionEnums.contains(PermissionEnum.ACCESS));

               return item;
            });
         }
      }

      return new ArrayList<>(map.values());
   }

   @Transactional(readOnly = true)
   @Override
   public Role getRole(Integer id) {
      Role role = roleDao.get(id);

      if(role == null) {
         LOGGER.info("Role is not found: {}", id);
      }

      return role;
   }

   @Transactional(readOnly = true)
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

   @Transactional
   @Override
   public void insertPermission(Integer roleId, Permission permission) {
      Role role = getRole(roleId);

      if(roleId == null) {
         return;
      }

      Log log = createLog(role);

      try {
         Set<Permission> permissions = role.getPermissions();

         if(permissions == null) {
            permissions = new HashSet<>();
            role.setPermissions(permissions);
         }

         permissions.add(permission);

         updateRole(role);
      }
      catch(Exception e) {
         log.setMessage("Failed: " + e.getMessage());

         throw e;
      }
      finally {
         logService.insertLog(log);
      }
   }

   private Log createLog(Role role) {
      Log log = new Log();

      try {
         log.setAction(ActionType.Authorization.getLabel());
         log.setDate(new Date());
         log.setResource(ResourceEnum.Role.getLabel() + ":" + role.getName());
         log.setCustomer(userHandler.getAuditUser());
         log.setIp(WebMvcUtil.getIP());
      }
      catch(Exception ex) {
         LOGGER.error("Create Log obj error", ex);
      }

      return log;
   }

   @Transactional
   @Override
   public void clearPermission(Integer roleId) {
      Role role = getRole(roleId);

      if(roleId == null) {
         return;
      }

      Log log = createLog(role);

      try {
         role.clearPermissions();

         updateRole(role);
      }
      catch (Exception e) {
         log.fillErrorMessage(e);

         throw e;
      }
      finally {
         logService.insertLog(log);
      }
   }

   private final UserHandler userHandler;
   private final LogService logService;
   private final RoleDao roleDao;

   private static final Logger LOGGER = LoggerFactory.getLogger(RoleServiceImpl.class);
}
