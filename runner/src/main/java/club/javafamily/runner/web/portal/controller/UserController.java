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

package club.javafamily.runner.web.portal.controller;

import club.javafamily.runner.domain.Customer;
import club.javafamily.runner.service.CustomerService;
import club.javafamily.runner.util.SecurityUtil;
import club.javafamily.runner.web.security.model.JfPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(SecurityUtil.API_VERSION)
public class UserController {

   @GetMapping("/public/principal")
   public JfPrincipal getCurrentUser() {
      JfPrincipal principal = new JfPrincipal();
      Customer user = customerService.getCurrentCustomer();
      principal.setAuthenticated(user != null);

      if(user == null) {
         principal.setUserName(SecurityUtil.Anonymous);
      }
      else {
         principal.setId(user.getId());
         principal.setAccount(user.getAccount());
         principal.setUserName(user.getName());
         principal.setEmail(user.getEmail());
      }

      return principal;
   }

   @GetMapping("/public/ping")
   public String ping() {
      return "{\"result\":\"pong\"}";
   }

   @Autowired
   public UserController(CustomerService customerService) {
      this.customerService = customerService;
   }

   private final CustomerService customerService;
}
