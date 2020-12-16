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
   private Integer id;
   private String label;
   private ResourceSettingType type;

   public AssignedToItem() {
   }

   public AssignedToItem(Integer id, String label, ResourceSettingType type) {
      this.id = id;
      this.label = label;
      this.type = type;
   }

   public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
   }

   public ResourceSettingType getType() {
      return type;
   }

   public void setType(ResourceSettingType type) {
      this.type = type;
   }

   public String getLabel() {
      return label;
   }

   public void setLabel(String label) {
      this.label = label;
   }
}
