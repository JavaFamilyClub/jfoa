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

package club.javafamily.runner.web.em.role;

import club.javafamily.runner.common.MessageException;
import club.javafamily.runner.common.model.data.TreeNodeModel;
import club.javafamily.runner.domain.Role;
import club.javafamily.runner.service.RoleService;
import club.javafamily.runner.util.I18nUtil;
import club.javafamily.runner.util.SecurityUtil;
import club.javafamily.runner.web.em.model.RoleManagerModel;
import io.swagger.annotations.*;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(SecurityUtil.API_VERSION)
@Api("Role Manager")
public class RoleController {

   @Autowired
   public RoleController(RoleService roleService) {
      this.roleService = roleService;
   }

   @RequiresUser
   @ApiOperation(
      value = "Get roles tree",
      httpMethod = "GET",
      response = RoleManagerModel.class
   )
   @GetMapping("/roles/tree")
   public RoleManagerModel getRolesTreeModel() {
      List<Role> roles = roleService.getRoles();

      return new RoleManagerModel(buildTree(roles));
   }

   private TreeNodeModel buildTree(List<Role> roles) {
      return TreeNodeModel.build()
         .setLabel(I18nUtil.getString("Roles"))
         .setTooltip(I18nUtil.getString("Roles"))
         .setPath("/")
         .setChildren(roles.stream()
            .map(this::buildTreeNode)
            .collect(Collectors.toList()));
   }

   private TreeNodeModel buildTreeNode(Role role) {
      return TreeNodeModel.build()
         .setLabel(role.getName())
         .setTooltip(role.getName())
         .setPath("/" + role.getName())
         .setLeaf(true)
         .setData(role);
   }

   @RequiresUser
   @ApiOperation(
      value = "Delete Role",
      httpMethod = "DELETE"
   )
   @DeleteMapping("/role/{ids}")
   public void deleteRole(@ApiParam(name = "role ids", required = true, example = "2,3") @PathVariable("ids") Integer[] ids) {
      roleService.deleteRoles(ids);
   }

   @RequiresUser
   @ApiOperation(
      value = "Add Role",
      httpMethod = "POST"
   )
   @PostMapping("/role/{name}")
   public Integer addRole(@ApiParam(name = "Role name", required = true, example = "roleName") @PathVariable("name") String roleName) {
      Role existRole = roleService.getRoleByName(roleName);

      if(existRole != null) {
         throw new MessageException(I18nUtil.getString("em.role.existError", roleName));
      }

      Role addRole = new Role(roleName);

      return roleService.addRole(addRole);
   }

   private final RoleService roleService;
}
