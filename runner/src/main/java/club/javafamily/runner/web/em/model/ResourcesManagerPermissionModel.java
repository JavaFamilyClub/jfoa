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

import java.util.List;

public class ResourcesManagerPermissionModel {
   private int id;
   private String resourceLabel;
   private List<ResourceItemSettingModel> items;

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

   public List<ResourceItemSettingModel> getItems() {
      return items;
   }

   public void setItems(List<ResourceItemSettingModel> items) {
      this.items = items;
   }
}
