/*
 * Copyright (c) 2019, JavaFamily Technology Corp, All Rights Reserved.
 *
 * The software and information contained herein are copyrighted and
 * proprietary to AngBoot Technology Corp. This software is furnished
 * pursuant to a written license agreement and may be used, copied,
 * transmitted, and stored only in accordance with the terms of such
 * license and with the inclusion of the above copyright notice. Please
 * refer to the file "COPYRIGHT" for further copyright and licensing
 * information. This software and information or any other copies
 * thereof may not be provided or otherwise made available to any other
 * person.
 */

package club.javafamily.runner.web.em.settings.model;

import club.javafamily.runner.domain.Role;

import java.io.Serializable;

public class RoleVo implements Serializable {
   protected Integer id;
   protected String name;
   protected String description;
   protected boolean defaultRole;
   protected boolean administrator;

   public static RoleVo buildFromDomain(Role role) {
      assert role != null;
      RoleVo vo = new RoleVo();

      vo.setId(role.getId());
      vo.setName(role.getName());
      vo.setAdministrator(role.isAdministrator());
      vo.setDescription(role.getDescription());
      vo.setDefaultRole(role.isDefaultRole());

      return vo;
   }

   public void updateDomain(Role role) {
      role.setName(this.name);
      role.setDefaultRole(this.defaultRole);
      role.setDefaultRole(this.defaultRole);
      role.setAdministrator(this.administrator);
   }

   public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getDescription() {
      return description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public boolean isDefaultRole() {
      return defaultRole;
   }

   public void setDefaultRole(boolean defaultRole) {
      this.defaultRole = defaultRole;
   }

   public boolean isAdministrator() {
      return administrator;
   }

   public void setAdministrator(boolean administrator) {
      this.administrator = administrator;
   }

   @Override
   public String toString() {
      return "RoleEditViewModel{" +
         "id=" + id +
         ", name='" + name + '\'' +
         ", description='" + description + '\'' +
         ", defaultRole=" + defaultRole +
         ", administrator=" + administrator +
         '}';
   }
}
