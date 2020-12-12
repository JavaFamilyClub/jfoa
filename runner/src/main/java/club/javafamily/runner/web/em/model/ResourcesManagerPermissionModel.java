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

package club.javafamily.runner.web.em.model;

import club.javafamily.commons.enums.PermissionEnum;
import club.javafamily.runner.domain.Role;

import java.util.EnumSet;
import java.util.Map;

public class ResourcesManagerPermissionModel {
   private int id;
   private String resourceLabel;
   private Map<Role, EnumSet<PermissionEnum>> map;

   public ResourcesManagerPermissionModel() {
   }

   public ResourcesManagerPermissionModel(int id) {
      this.id = id;
   }

   public String getResourceLabel() {
      return resourceLabel;
   }

   public void setResourceLabel(String resourceLabel) {
      this.resourceLabel = resourceLabel;
   }

   public int getId() {
      return id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public Map<Role, EnumSet<PermissionEnum>> getMap() {
      return map;
   }

   public void setMap(Map<Role, EnumSet<PermissionEnum>> map) {
      this.map = map;
   }
}
