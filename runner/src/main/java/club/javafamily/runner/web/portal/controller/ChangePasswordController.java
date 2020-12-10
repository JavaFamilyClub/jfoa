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

import club.javafamily.runner.annotation.Audit;
import club.javafamily.runner.annotation.AuditObject;
import club.javafamily.runner.common.MessageException;
import club.javafamily.runner.common.service.NotifyHandle;
import club.javafamily.runner.domain.Customer;
import club.javafamily.commons.enums.ActionType;
import club.javafamily.runner.enums.ResourceEnum;
import club.javafamily.runner.service.CustomerService;
import club.javafamily.runner.util.SecurityUtil;
import club.javafamily.runner.vo.ChangePasswordDialogModel;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(SecurityUtil.API_VERSION)
public class ChangePasswordController {

   @RequiresAuthentication
   @PutMapping("/customer/password/verify")
   public boolean verifyOldPassword(@RequestBody() ChangePasswordDialogModel model) {
      Customer customer = customerService.getCustomerByAccount(model.getAccount());

      return SecurityUtil.generatorPassword(customer.getAccount(), model.getOldPwd())
         .equals(customer.getPassword());
   }

   @RequiresAuthentication
   @Audit(
      value = ResourceEnum.Password,
      actionType = ActionType.MODIFY
   )
   @PutMapping("/customer/password")
   public void changePassword(@RequestBody() @AuditObject("getAccount()") ChangePasswordDialogModel model,
                              HttpServletRequest request)
   {
      String account = model.getAccount();
      String newPwd = model.getNewPwd();
      String confirmPwd = model.getConfirmPwd();

      if(StringUtils.isEmpty(newPwd) || StringUtils.isEmpty(confirmPwd)) {
         throw new MessageException("The password cannot be empty.");
      }

      if(!newPwd.equals(confirmPwd)) {
         throw new MessageException("The password and the confirmation password are inconsistent.");
      }

      Customer customer = customerService.getCustomerByAccount(account);

      if(customer == null) {
         throw new MessageException("User is not exist!");
      }

      customer.setPassword(SecurityUtil.generatorPassword(account, newPwd));
      customerService.updateCustomer(customer);

      // send notify
      notifyHandle.getNotifyService(customer).changePasswordNotify(customer, request);

      // logout and redirect to login page
//      SecurityUtils.getSubject().logout();
//
//      return "redirect:/login";
   }

   @Autowired
   public ChangePasswordController(CustomerService customerService,
                                   NotifyHandle notifyHandle)
   {
      this.customerService = customerService;
      this.notifyHandle = notifyHandle;
   }

   private final NotifyHandle notifyHandle;
   private final CustomerService customerService;

   private static final Logger LOGGER = LoggerFactory.getLogger(ChangePasswordController.class);
}
