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

import club.javafamily.runner.domain.Role;

public class RoleEditViewModel {

   private RoleVo role;
   private RoleAssignedToModel assignedToModel;

   public static RoleEditViewModel buildFromDomain(Role role) {
      RoleEditViewModel model = new RoleEditViewModel();

      model.role = RoleVo.buildFromDomain(role);

      return model;
   }

   public RoleVo getRole() {
      return role;
   }

   public void setRole(RoleVo role) {
      this.role = role;
   }

   public RoleAssignedToModel getAssignedToModel() {
      return assignedToModel;
   }

   public void setAssignedToModel(RoleAssignedToModel assignedToModel) {
      this.assignedToModel = assignedToModel;
   }
}
