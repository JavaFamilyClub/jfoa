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

package club.javafamily.runner.web.em.security;

import club.javafamily.commons.enums.ResourceSettingType;
import club.javafamily.runner.common.model.data.TreeNodeModel;
import club.javafamily.runner.domain.Permission;
import club.javafamily.runner.domain.Role;
import club.javafamily.runner.enums.ResourceEnum;
import club.javafamily.runner.service.PermissionService;
import club.javafamily.runner.service.RoleService;
import club.javafamily.runner.util.SecurityUtil;
import club.javafamily.runner.web.em.model.*;
import club.javafamily.runner.web.em.role.RoleController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
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

      ResourcesManagerPermissionModel resourcesPermissionModel
         = getResourcesPermissionModel(model.getId());

      List<ResourceItemSettingModel> oldItems = resourcesPermissionModel.getItems();
      List<ResourceItemSettingModel> items = model.getItems();

      if(CollectionUtils.isEmpty(oldItems)) {
         // add
         for(ResourceItemSettingModel item : items) {
            Integer roleId = item.getRoleId();

            // role
            if(roleId != null && item.getType() == ResourceSettingType.Role) {
               Permission permission = new Permission();
               permission.setResource(resourceId);
               permission.setOperator(item.buildPermissionOperator());

               roleService.insertPermission(roleId, permission);
            }
         }
      }
      else if(CollectionUtils.isEmpty(items)) {
         // TODO delete
      }
      else {
         // TODO mixed operation
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
