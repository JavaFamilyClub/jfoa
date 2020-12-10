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

import club.javafamily.runner.util.SecurityUtil;
import club.javafamily.runner.web.em.model.ResourcesManagerModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(SecurityUtil.API_VERSION)
public class SecurityResourcesController {

   @GetMapping("/security/resources")
   public ResourcesManagerModel getSecurityResourcesModel() {
      ResourcesManagerModel model = new ResourcesManagerModel();

      model.setTree(resourcesManager.getResourcesTree());

      return model;
   }

   @Autowired
   public SecurityResourcesController(ResourcesManager resourcesManager) {
      this.resourcesManager = resourcesManager;
   }

   private final ResourcesManager resourcesManager;
}
