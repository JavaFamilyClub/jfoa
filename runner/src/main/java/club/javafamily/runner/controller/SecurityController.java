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

package club.javafamily.runner.controller;

import club.javafamily.runner.annotation.Audit;
import club.javafamily.runner.annotation.AuditObject;
import club.javafamily.runner.common.MessageException;
import club.javafamily.runner.common.model.amqp.RegisterUserInfo;
import club.javafamily.runner.common.service.RedisClient;
import club.javafamily.runner.domain.Customer;
import club.javafamily.commons.enums.ActionType;
import club.javafamily.runner.enums.ResourceEnum;
import club.javafamily.runner.service.CustomerService;
import club.javafamily.runner.util.*;
import club.javafamily.runner.vo.EmailCustomerVO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.*;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

import static club.javafamily.runner.util.SecurityUtil.API_VERSION;
import static club.javafamily.runner.util.SecurityUtil.REGISTERED_USER_STORE_PREFIX;

@Controller
public class SecurityController {

   @Audit(value = ResourceEnum.Customer, actionType = ActionType.Login)
   @PostMapping(API_VERSION + "/login")
   public String login(@RequestParam @AuditObject String userName,
                       @RequestParam String password,
                       @RequestParam(required = false) boolean rememberMe,
                       HttpServletRequest request)
   {
      if(!StringUtils.hasText(password)) {
         throw new IncorrectCredentialsException(
            I18nUtil.getString("user.pwd.warn.required"));
      }

      Subject currentUser = SecurityUtils.getSubject();

      if(!currentUser.isAuthenticated()) {
         UsernamePasswordToken token = new UsernamePasswordToken(userName, password);

         token.setRememberMe(rememberMe);

         currentUser.login(token);
      }

      SavedRequest savedRequest= WebUtils.getSavedRequest(request);

      if(savedRequest != null && StringUtils.hasText(savedRequest.getRequestUrl())) {
         String requestUrl = savedRequest.getRequestUrl();

         return "redirect:" + requestUrl;
      }

      // redirect index page when login success.
      return "redirect:/";
   }

   @GetMapping("signup")
   public String gotoSignupPage(ModelMap map) {
      if(map.get("customer") == null) {
         EmailCustomerVO customerVO = new EmailCustomerVO();

         map.put("customer", customerVO);
      }

      return "signup";
   }

   @PostMapping(API_VERSION + "/signup")
   public String signup(@Valid @ModelAttribute EmailCustomerVO customerVO,
                        BindingResult bindingResult,
                        HttpServletRequest request,
                        ModelMap map)
   {
      if(!WebMvcUtil.verifyCode(request)) {
         map.put("message", I18nUtil.getString("security.errorMsg.verifyCode"));
         map.put("customer", customerVO);

         return "signup";
      }

      List<ObjectError> allErrors = bindingResult.getAllErrors();
      StringBuilder sb = new StringBuilder();

      for(int i = 0; i < allErrors.size(); i++) {
         ObjectError error = allErrors.get(i);

         if(error instanceof FieldError) {
            sb.append(((FieldError) error).getField())
               .append(": ")
               .append(error.getDefaultMessage());
         }
         else {
            sb.append(error.getDefaultMessage());
         }

         if(i < allErrors.size() - 1) {
            sb.append("\n");
         }
      }

      if(StringUtils.hasText(sb)) {
         throw new MessageException(sb.toString());
      }

      String account = customerVO.getAccount();
      Customer user = customerService.getCustomerByAccount(account);

      if(user != null) {
         throw new MessageException("The account has been registered: " + account);
      }

      StringBuilder path = SecurityUtil.getBaseUrl(request);
      String loginLink = path.toString() + "/login";
      path.append(API_VERSION);
      path.append("/customer/verify");

      customerService.signup(customerVO, path.toString());
      map.put("loginLink", loginLink);

      // goto login after sign up.
      return "signupSuccess";
   }

   @GetMapping(API_VERSION + "/customer/verify")
   public String verify(String token,
                        String identity,
                        ModelMap modelMap,
                        HttpServletRequest request)
   {
      String key = REGISTERED_USER_STORE_PREFIX + identity;
      RegisterUserInfo info = redisClient.get(key);
      boolean verify = false;

      StringBuilder redirectLink = SecurityUtil.getBaseUrl(request);

      if(info != null) {
         String realToken = info.getToken();

         if(token.equals(realToken)) {
            Customer user = info.convertModel();
            user.setActive(true);
            customerService.insertCustomer(user);
            redisClient.delete(key);
            verify = true;
            redirectLink.append("/login");
         }
         else {
            redirectLink.append("/signup");
            modelMap.put("reason", "Token is not correct!");
         }
      }
      else {
         redirectLink.append("/signup");
         modelMap.put("reason", "This token is expired!");
      }

      modelMap.put("result", verify);
      modelMap.put("redirectLink", redirectLink);

      return "verifyResult";
   }

   @GetMapping("/error")
   public String gotoErrorPage() {
      return "/error/exception";
   }

   @Autowired
   public SecurityController(CustomerService customerService,
                             RedisClient<RegisterUserInfo> redisClient)
   {
      this.redisClient = redisClient;
      this.customerService = customerService;
   }

   private final CustomerService customerService;
   private final RedisClient<RegisterUserInfo> redisClient;

   private static final Logger LOGGER = LoggerFactory.getLogger(SecurityController.class);
}
