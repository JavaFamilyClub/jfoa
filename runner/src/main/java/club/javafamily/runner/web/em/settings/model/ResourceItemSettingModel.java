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

import club.javafamily.commons.enums.PermissionEnum;
import club.javafamily.commons.enums.ResourceSettingType;
import club.javafamily.runner.domain.Permission;

import java.util.Objects;

public class ResourceItemSettingModel {
   private Integer id; // permission id
   private Integer roleId;
   private ResourceSettingType type;
   private String name;
   private boolean read;
   private boolean write;
   private boolean delete;
   private boolean access;
   private boolean admin;

   public ResourceItemSettingModel() {
   }

   public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
   }

   public Integer getRoleId() {
      return roleId;
   }

   public void setRoleId(Integer roleId) {
      this.roleId = roleId;
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

   public boolean isRead() {
      return read;
   }

   public void setRead(boolean read) {
      this.read = read;
   }

   public boolean isWrite() {
      return write;
   }

   public void setWrite(boolean write) {
      this.write = write;
   }

   public boolean isDelete() {
      return delete;
   }

   public void setDelete(boolean delete) {
      this.delete = delete;
   }

   public boolean isAccess() {
      return access;
   }

   public void setAccess(boolean access) {
      this.access = access;
   }

   public boolean isAdmin() {
      return admin;
   }

   public void setAdmin(boolean admin) {
      this.admin = admin;
   }

   public Permission convertPermissionEntity(int resource) {
      return new Permission(id, resource, buildPermissionOperator());
   }

   public Integer buildPermissionOperator() {
      int operator = 0;

      if(read) {
         operator = operator | PermissionEnum.READ.getPermission();
      }

      if(write) {
         operator = operator | PermissionEnum.WRITE.getPermission();
      }

      if(delete) {
         operator = operator | PermissionEnum.DELETE.getPermission();
      }

      if(access) {
         operator = operator | PermissionEnum.ACCESS.getPermission();
      }

      if(admin) {
         operator = operator | PermissionEnum.ADMIN.getPermission();
      }

      return operator;
   }

   @Override
   public boolean equals(Object o) {
      if(this == o) {
         return true;
      }

      if(o == null || getClass() != o.getClass()) {
         return false;
      }

      ResourceItemSettingModel that = (ResourceItemSettingModel) o;

      if(!Objects.equals(id, that.id)) {
         return false;
      }

      if(!roleId.equals(that.roleId)) {
         return false;
      }

      if(type != that.type) {
         return false;
      }

      return name.equals(that.name);
   }

   @Override
   public int hashCode() {
      int result = id != null ? id.hashCode() : 0;
      result = 31 * result + roleId.hashCode();
      result = 31 * result + type.hashCode();
      result = 31 * result + name.hashCode();

      return result;
   }
}
