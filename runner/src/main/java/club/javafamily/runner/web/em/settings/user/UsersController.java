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

package club.javafamily.runner.web.em.settings.user;

import club.javafamily.commons.enums.ResourceSettingType;
import club.javafamily.runner.common.MessageException;
import club.javafamily.runner.common.model.data.TreeNodeModel;
import club.javafamily.runner.domain.Customer;
import club.javafamily.runner.service.CustomerService;
import club.javafamily.runner.service.UserHandler;
import club.javafamily.runner.util.I18nUtil;
import club.javafamily.runner.util.SecurityUtil;
import club.javafamily.runner.web.em.settings.model.CustomerVO;
import club.javafamily.runner.web.em.settings.model.UserManagerModel;
import io.swagger.annotations.*;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(SecurityUtil.API_VERSION)
@Api("User Manager")
public class UsersController {

   @Autowired
   public UsersController(CustomerService customerService) {
      this.customerService = customerService;
   }

   @RequiresUser
   @ApiOperation(
      value = "Get users",
      httpMethod = "GET",
      response = UserManagerModel.class
   )
   @GetMapping("/users")
   public UserManagerModel getUsers() {
      List<Customer> customers = customerService.getCustomers();

      List<CustomerVO> voList = customers.stream()
         .map(CustomerVO::buildFromDomain)
         .collect(Collectors.toList());

      return new UserManagerModel(voList);
   }

   @RequiresUser
   @ApiOperation(
      value = "Get users tree",
      httpMethod = "GET",
      response = TreeNodeModel.class
   )
   @GetMapping("/users/tree")
   public TreeNodeModel getUsersTree() {
      List<Customer> users = customerService.getCustomers();

      List<TreeNodeModel> children = users.stream()
         .map(this::buildUserNode)
         .collect(Collectors.toList());

      return TreeNodeModel.build()
         .setLabel(I18nUtil.getString("Users"))
         .setPath(TreeNodeModel.ROOT_PATH)
         .setChildren(children)
         .setType(ResourceSettingType.User);
   }

   private TreeNodeModel buildUserNode(Customer user) {
      return TreeNodeModel.build()
         .setLabel(UserHandler.getDisplayLabel(user))
         .setValue(user.getId() + "")
         .setData(CustomerVO.buildFromDomain(user))
         .setPath(TreeNodeModel.ROOT_PATH + "/" + user.getId())
         .setType(ResourceSettingType.User);
   }

   @RequiresUser
   @ApiOperation(
      value = "Delete User",
      httpMethod = "DELETE"
   )
   @DeleteMapping("/user/{id}")
   public void deleteUser(@ApiParam(value = "customer id") @PathVariable("id") Integer id) {
      Customer dUser = customerService.getCustomer(id);

      if(SecurityUtil.isAdmin(dUser)) {
         throw new MessageException(I18nUtil.getString("common.noPermission"));
      }

      customerService.deleteCustomer(dUser);
   }

   private final CustomerService customerService;
}
