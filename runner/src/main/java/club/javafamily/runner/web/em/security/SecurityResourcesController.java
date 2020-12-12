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

import club.javafamily.commons.enums.PermissionEnum;
import club.javafamily.runner.domain.Permission;
import club.javafamily.runner.domain.Role;
import club.javafamily.runner.enums.ResourceEnum;
import club.javafamily.runner.service.PermissionService;
import club.javafamily.runner.util.SecurityUtil;
import club.javafamily.runner.web.em.model.ResourcesManagerModel;
import club.javafamily.runner.web.em.model.ResourcesManagerPermissionModel;
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

      if(resource == null) {
         LOGGER.info("Resource is not found: {}", resourceId);
         return null;
      }

      ResourcesManagerPermissionModel model
         = new ResourcesManagerPermissionModel(resourceId);
      model.setResourceLabel(resource.getLabel());

      List<Permission> permissions = permissionService
         .getPermissionsByResource(resourceId);

      Map<Role, EnumSet<PermissionEnum>> map = new HashMap<>();

      for(Permission permission : permissions) {
         for(Role role : permission.getRoles()) {
            EnumSet<PermissionEnum> permissionEnums
               = SecurityUtil.parsePermissionOperator(permission.getOperator());

            map.compute(role, (k, oldValue) -> {
               if(oldValue == null) {
                  return permissionEnums;
               }

               oldValue.addAll(permissionEnums);

               return oldValue;
            });
         }
      }

      model.setMap(map);

      return model;
   }

   @Autowired
   public SecurityResourcesController(PermissionService permissionService,
                                      ResourcesManager resourcesManager)
   {
      this.permissionService = permissionService;
      this.resourcesManager = resourcesManager;
   }

   private final PermissionService permissionService;
   private final ResourcesManager resourcesManager;

   private static final Logger LOGGER = LoggerFactory.getLogger(SecurityResourcesController.class);
}
