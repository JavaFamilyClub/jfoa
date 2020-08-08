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

package club.javafamily.runner.web.portal.controller;

import club.javafamily.runner.domain.Customer;
import club.javafamily.runner.service.CustomerService;
import club.javafamily.runner.util.SecurityUtil;
import club.javafamily.runner.web.portal.model.UserProfileDialogModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(SecurityUtil.API_VERSION)
public class UserProfileDialogController {

   @Autowired
   public UserProfileDialogController(CustomerService customerService) {
      this.customerService = customerService;
   }

   @GetMapping("/user/profile")
   public UserProfileDialogModel getModel() {
      Customer customer = customerService.getCurrentCustomer();

      if(customer == null) {
         return null;
      }

      UserProfileDialogModel model = new UserProfileDialogModel();

      model.setId(customer.getId());
      model.setAccount(customer.getAccount());
      model.setEmail(customer.getEmail());
      model.setName(customer.getName());

      return model;
   }

   @PutMapping("/user/profile")
   public void updateModel(@RequestBody UserProfileDialogModel model) {
      Integer id = model.getId();
      Customer customer = customerService.getCustomer(id);

      if(customer == null) {
         return;
      }

      customer.setName(model.getName());
      // unsupport modify account now.
//      customer.setAccount(model.getAccount());
      customer.setEmail(model.getEmail());

      customerService.updateCustomer(customer);
   }

   private final CustomerService customerService;
}
