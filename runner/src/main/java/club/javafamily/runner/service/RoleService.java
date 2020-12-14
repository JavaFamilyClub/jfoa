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

package club.javafamily.runner.service;

import club.javafamily.runner.domain.Permission;
import club.javafamily.runner.domain.Role;
import club.javafamily.runner.web.em.model.ResourceItemSettingModel;

import java.util.List;

public interface RoleService {

   List<Role> getRoles();

   List<ResourceItemSettingModel> getRolesByResource(Integer resourceId);

   Role getRole(Integer id);

   Role getRoleByName(String name);

   void deleteRole(Role role);

   void deleteRoles(Integer[] ids);

   Integer addRole(Role role);

   void updateRole(Role role);

   void insertPermission(Integer roleId, Permission permission);

   void clearPermission(Integer roleId);

}
