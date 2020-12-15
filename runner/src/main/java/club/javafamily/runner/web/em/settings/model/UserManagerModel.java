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

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel("User Manager Model")
public class UserManagerModel {

   public UserManagerModel() {
   }

   public UserManagerModel(List<CustomerVO> users) {
      this.users = users;
   }

   public List<CustomerVO> getUsers() {
      return users;
   }

   public void setUsers(List<CustomerVO> users) {
      this.users = users;
   }

   @ApiModelProperty("User List")
   private List<CustomerVO> users;
}
