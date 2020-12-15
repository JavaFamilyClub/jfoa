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

package club.javafamily.runner.web.em.settings.security;

import club.javafamily.commons.enums.ResourceSettingType;
import club.javafamily.runner.common.model.data.TreeNodeModel;
import club.javafamily.runner.domain.Permission;
import club.javafamily.runner.domain.Role;
import club.javafamily.runner.enums.ResourceEnum;
import club.javafamily.runner.service.PermissionService;
import club.javafamily.runner.service.RoleService;
import club.javafamily.runner.util.SecurityUtil;
import club.javafamily.runner.web.em.settings.model.*;
import club.javafamily.runner.web.em.settings.role.RoleController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(SecurityUtil.API_VERSION)
public class SecurityResourcesController {

   @GetMapping("/security/resources")
   public ResourcesManagerModel getSecurityResourcesModel() {
      ResourcesManagerModel model = new ResourcesManagerModel();

      model.setTree(resourcesManager.getResourcesTree());

      return model;
   }

   @GetMapping("/security/resources/permission/{id}")
   public ResourcesManagerPermissionModel getResourcesPermissionModel(
      @PathVariable("id") Integer resourceId)
   {
      ResourceEnum resource = ResourceEnum.parse(resourceId);

      if(resource == null || resource == ResourceEnum.Unknown) {
         LOGGER.info("Resource is not found: {}", resourceId);
         return null;
      }

      ResourcesManagerPermissionModel model
         = new ResourcesManagerPermissionModel(resourceId);
      model.setResourceLabel(resource.getLabel());
      model.setItems(roleService.getRolesByResource(resourceId));

      return model;
   }

   @PostMapping("/security/resources/permission/{id}/{roleId}")
   public void setResourcesPermission(@PathVariable("id") Integer resourceId,
                                      @PathVariable("roleId") Integer roleId)
   {
      if(roleId == null) {
         LOGGER.info("Role is not found: {}", roleId);
         return;
      }

      Permission permission = new Permission();
      permission.setResource(resourceId);

      roleService.insertPermission(roleId, permission);
   }

   @PutMapping("/security/resources/permission")
   public void updateResourcesPermissionModel(
      @RequestBody ResourcesManagerPermissionModel model)
   {
      int resourceId = model.getId();

      ResourceEnum resource = ResourceEnum.parse(resourceId);

      if(resource == null || resource == ResourceEnum.Unknown) {
         LOGGER.info("Resource is not found: {}", resourceId);
         return;
      }

      List<ResourceItemSettingModel> add = new ArrayList<>();
      List<ResourceItemSettingModel> delete = new ArrayList<>();
      List<ResourceItemSettingModel> update = new ArrayList<>();

      checkItemsStatus(model, add, delete, update);

      updateItems(resourceId, add, delete, update);
   }

   private void checkItemsStatus(ResourcesManagerPermissionModel model,
                                 List<ResourceItemSettingModel> add,
                                 List<ResourceItemSettingModel> delete,
                                 List<ResourceItemSettingModel> update)
   {
      ResourcesManagerPermissionModel resourcesPermissionModel
         = getResourcesPermissionModel(model.getId());

      List<ResourceItemSettingModel> oldItems = resourcesPermissionModel.getItems();
      List<ResourceItemSettingModel> items = model.getItems();

      // check add, update
      for(ResourceItemSettingModel item : items) {
         if(!oldItems.contains(item)) {
            add.add(item);
         }
         else {
            update.add(item);
         }
      }

      // check delete
      for(ResourceItemSettingModel oldItem : oldItems) {
         if(!items.contains(oldItem)) {
            delete.add(oldItem);
         }
      }
   }

   private void updateItems(int resourceId,
                            List<ResourceItemSettingModel> add,
                            List<ResourceItemSettingModel> delete,
                            List<ResourceItemSettingModel> update)
   {
      for(ResourceItemSettingModel addItem : add) {
         Integer roleId = addItem.getRoleId();

         // role
         if(roleId != null && addItem.getType() == ResourceSettingType.Role) {
            Permission permission = addItem.convertPermissionEntity(resourceId);

            roleService.insertPermission(roleId, permission);
         }
      }

      for(ResourceItemSettingModel deleteItem : delete) {
         Integer roleId = deleteItem.getRoleId();

         // role
         if(roleId != null && deleteItem.getType() == ResourceSettingType.Role) {
            roleService.clearPermission(deleteItem.getRoleId());
         }
      }

      for(ResourceItemSettingModel updateItem : update) {
         permissionService.update(
            updateItem.getName(), updateItem.convertPermissionEntity(resourceId));
      }
   }

   @GetMapping("/security/resources/permission/tree")
   public TreeNodeModel getResourceTree() {
      List<Role> roles = roleService.getRoles();
      String rolePath = "/roles";
      TreeNodeModel roleNode = roleController.buildTree(roles, rolePath);

      return TreeNodeModel.build()
         .setLabel("root")
         .setPath(rolePath)
         .setChildren(Collections.singletonList(roleNode))
         ;
   }

   @Autowired
   public SecurityResourcesController(RoleController roleController, RoleService roleService,
                                      PermissionService permissionService,
                                      ResourcesManager resourcesManager)
   {
      this.roleController = roleController;
      this.roleService = roleService;
      this.permissionService = permissionService;
      this.resourcesManager = resourcesManager;
   }

   private final RoleController roleController;
   private final RoleService roleService;
   private final PermissionService permissionService;
   private final ResourcesManager resourcesManager;

   private static final Logger LOGGER = LoggerFactory.getLogger(SecurityResourcesController.class);
}
