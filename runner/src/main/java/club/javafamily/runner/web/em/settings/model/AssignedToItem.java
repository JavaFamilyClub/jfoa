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

package club.javafamily.runner.web.em.settings.model;

import club.javafamily.commons.enums.ResourceSettingType;

public class AssignedToItem {
   private String name;
   private ResourceSettingType type;

   public AssignedToItem() {
   }

   public AssignedToItem(String name, ResourceSettingType type) {
      this.name = name;
      this.type = type;
   }

   public ResourceSettingType getType() {
      return type;
   }

   public void setType(ResourceSettingType type) {
      this.type = type;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }
}
