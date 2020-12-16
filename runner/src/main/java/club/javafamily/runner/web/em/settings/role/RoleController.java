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

package club.javafamily.runner.web.em.settings.role;

import club.javafamily.commons.enums.ResourceSettingType;
import club.javafamily.commons.utils.Tool;
import club.javafamily.runner.common.MessageException;
import club.javafamily.runner.common.model.data.TreeNodeModel;
import club.javafamily.runner.domain.Customer;
import club.javafamily.runner.domain.Role;
import club.javafamily.runner.service.*;
import club.javafamily.runner.util.I18nUtil;
import club.javafamily.runner.util.SecurityUtil;
import club.javafamily.runner.web.em.settings.model.*;
import io.swagger.annotations.*;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping(SecurityUtil.API_VERSION)
@Api("Role Manager")
public class RoleController {

   @RequiresUser
   @ApiOperation(
      value = "Get roles tree",
      httpMethod = "GET",
      response = RoleManagerModel.class
   )
   @GetMapping("/roles/tree")
   public RoleManagerModel getRolesTreeModel() {
      List<Role> roles = roleService.getRoles();

      return new RoleManagerModel(buildTree(roles, "/"));
   }

   public TreeNodeModel buildTree(List<Role> roles, String path) {
      return TreeNodeModel.build()
         .setLabel(I18nUtil.getString("Roles"))
         .setTooltip(I18nUtil.getString("Roles"))
         .setPath(path)
         .setChildren(roles.stream()
            .map(role -> this.buildTreeNode(role, path))
            .collect(Collectors.toList()));
   }

   private TreeNodeModel buildTreeNode(Role role, String basePath) {
      return TreeNodeModel.build()
         .setLabel(role.getName())
         .setTooltip(role.getName())
         .setPath(Tool.getTreePath(basePath, role.getName()))
         .setLeaf(true)
         .setData(RoleVo.buildFromDomain(role));
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

   @RequiresUser
   @ApiOperation(
      value = "Get Role Edit Model",
      httpMethod = "GET",
      response = RoleEditViewModel.class
   )
   @GetMapping("/role/{id}")
   public RoleEditViewModel getRoleEditViewModel(
      @ApiParam(name = "Role id", required = true, example = "3") @PathVariable("id") int id)
   {
      Role role = roleService.getRole(id);

      RoleEditViewModel model = RoleEditViewModel.buildFromDomain(role);

      List<Customer> users = userService.getCustomerByRole(id);

      if(users != null) {
         List<AssignedToItem> items = users.stream()
            .map(user -> new AssignedToItem(
               user.getId(), UserHandler.getDisplayLabel(user), ResourceSettingType.User))
            .collect(Collectors.toList());

         model.setAssignedToModel(new RoleAssignedToModel(items));
      }

      return model;
   }

   @RequiresUser
   @ApiOperation(
      value = "Update Role",
      httpMethod = "PUT"
   )
   @PutMapping("/role/{id}")
   public void updateRole(
      @ApiParam(name = "Role id", required = true, example = "3") @PathVariable("id") int id,
      @RequestBody RoleEditViewModel roleEditModel)
   {
      if(roleEditModel == null || roleEditModel.getRole() == null
         || roleEditModel.getRole().getId() == null)
      {
         throw new MessageException(I18nUtil.getString("em.role.updateEmpty"));
      }

      RoleVo roleModel = roleEditModel.getRole();

      if(!Objects.equals(roleModel.getId(), id)) {
         throw new MessageException(
            I18nUtil.getString("em.role.updateNotMatch", id, roleModel.getId()));
      }

      // update role info
      Role role = roleService.getRole(id);
      roleModel.updateDomain(role);
      roleService.updateRole(role);

      // update assignedTo
      RoleAssignedToModel assignedToModel = roleEditModel.getAssignedToModel();

      List<AssignedToItem> add = new ArrayList<>();
      List<AssignedToItem> delete = new ArrayList<>();

      checkItemsStatus(id, assignedToModel, add, delete);

      updateItems(role, add, delete);
   }

   private void updateItems(Role role,
                            List<AssignedToItem> add,
                            List<AssignedToItem> delete)
   {
      Objects.requireNonNull(role, "Assigned to role is null.");

      for(AssignedToItem userInfo : add) {
         userService.addRole(userInfo.getId(), role);
      }

      for(AssignedToItem userInfo : delete) {
         userService.deleteRole(userInfo.getId(), role.getId());
      }
   }

   private void checkItemsStatus(Integer roleId,
                                 RoleAssignedToModel newAssignedToModel,
                                 List<AssignedToItem> add,
                                 List<AssignedToItem> delete)
   {
      RoleEditViewModel oldModel = getRoleEditViewModel(roleId);
      RoleAssignedToModel oldAssignedToModel = oldModel.getAssignedToModel();

      List<AssignedToItem> oldItems = oldAssignedToModel != null
         ? oldAssignedToModel.getItems()
         : null;
      List<AssignedToItem> items = newAssignedToModel != null
         ? newAssignedToModel.getItems()
         : null;

      // check add, update
      if(items != null) {
         for(AssignedToItem item : items) {
            if(oldItems == null || !oldItems.contains(item)) {
               add.add(item);
            }
         }
      }

      // check delete
      if(oldItems != null) {
         for(AssignedToItem oldItem : oldItems) {
            if(items == null || !items.contains(oldItem)) {
               delete.add(oldItem);
            }
         }
      }
   }

   @Autowired
   public RoleController(RoleService roleService,
                         CustomerService userService)
   {
      this.userService = userService;
      this.roleService = roleService;
   }

   private final RoleService roleService;
   private final CustomerService userService;
}
