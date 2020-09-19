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

package club.javafamily.runner.client;

import club.javafamily.runner.annotation.Audit;
import club.javafamily.runner.annotation.AuditObject;
import club.javafamily.runner.common.MessageException;
import club.javafamily.runner.domain.Customer;
import club.javafamily.runner.enums.ActionType;
import club.javafamily.runner.enums.ResourceEnum;
import club.javafamily.runner.service.CustomerService;
import club.javafamily.runner.util.SecurityUtil;
import club.javafamily.runner.vo.EmailCustomerVO;
import io.swagger.annotations.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static club.javafamily.runner.util.SecurityUtil.API_VERSION;

@Api("Security Api")
@RestController
@RequestMapping(SecurityUtil.CLIENT_API_VERSION)
public class SecurityApiController {

  @ApiOperation("Sign in")
  @Audit(value = ResourceEnum.Customer, actionType = ActionType.Login)
  @PostMapping("/login")
  public String login(@ApiParam(value = "User Name", required = true) @RequestParam @AuditObject String userName,
                      @ApiParam(value = "Password", required = true) @RequestParam String password,
                      @ApiParam(value = "rememberMe") @RequestParam(required = false) boolean rememberMe)
  {
    Subject currentUser = SecurityUtils.getSubject();

    if(!currentUser.isAuthenticated()) {
      UsernamePasswordToken token = new UsernamePasswordToken(userName, password);

      token.setRememberMe(rememberMe);

      try {
        currentUser.login(token);
      } catch (AuthenticationException e) {
        throw e;
      }
    }

    return null;
  }

  @ApiOperation("Logout")
  @GetMapping("/logout")
  public void logout() {
    Subject currentUser = SecurityUtils.getSubject();
    currentUser.logout();
  }

  @PostMapping("/public/signup")
  public void signup(@Valid @RequestBody EmailCustomerVO customerVO,
                     HttpServletRequest request)
  {
    String account = customerVO.getAccount();
    Customer user = customerService.getCustomerByAccount(account);

    if(user != null) {
      throw new MessageException("The account has been registered: " + account);
    }

    StringBuilder path = SecurityUtil.getBaseUrl(request);
    path.append(API_VERSION);
    path.append("/customer/verify");

    customerService.signup(customerVO, path.toString());
  }

  @Autowired
  public SecurityApiController(CustomerService customerService)
  {
    this.customerService = customerService;
  }

  private final CustomerService customerService;

  private static final Logger LOGGER = LoggerFactory.getLogger(SecurityApiController.class);
}
