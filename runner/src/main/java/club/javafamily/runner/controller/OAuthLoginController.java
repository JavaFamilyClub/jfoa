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

import club.javafamily.commons.enums.Gender;
import club.javafamily.commons.enums.UserType;
import club.javafamily.commons.utils.EnumUtil;
import club.javafamily.runner.config.OAuthUsernamePasswordToken;
import club.javafamily.runner.controller.model.OAuthAuthenticationException;
import club.javafamily.runner.domain.Customer;
import club.javafamily.runner.dto.*;
import club.javafamily.runner.rest.QueryEngine;
import club.javafamily.runner.rest.QueryEngineFactory;
import club.javafamily.runner.service.CustomerService;
import club.javafamily.runner.util.I18nUtil;
import club.javafamily.runner.util.WebMvcUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Controller
@Lazy
public class OAuthLoginController {

   @Autowired
   public OAuthLoginController(CustomerService customerService,
                               QueryEngineFactory engineFactory)
   {
      this.customerService = customerService;
      this.engineFactory = engineFactory;
   }

   @GetMapping("/public/oauth/auth")
   public String auth(@RequestParam String type) {
      QueryEngine<? extends RestUser> queryEngine = getQueryEngine(type);

      String authorizeUrl = queryEngine.getAuthorizeUrl();

      return WebMvcUtil.redirect(authorizeUrl);
   }

   private QueryEngine<? extends RestUser> getQueryEngine(String type) {
      UserType userType = EnumUtil.matchEnum(UserType.class, type);

      if(userType == null) {
         throw new OAuthAuthenticationException("Parse user type error for " + type);
      }

      return engineFactory.getQueryEngine(userType);
   }

   @GetMapping("/public/oauth/callback")
   public String callback(@RequestParam("code") String code,
                          @RequestParam("state") String state,
                          HttpServletRequest request)
   {
      QueryEngine<? extends RestUser> queryEngine = getQueryEngine(state);

      AccessTokenResponse accessTokenResponse
         = queryEngine.queryAccessToken(code, state);

      authentication(queryEngine, accessTokenResponse);

      return WebMvcUtil.redirectOrElse(request, "/");
   }

   private void authentication(QueryEngine<? extends RestUser> queryEngine,
                               AccessTokenResponse accessTokenResponse)
   {
      if(accessTokenResponse == null) {
         LOGGER.error("AccessTokenResponse is null.");
         return;
      }

      RestUser user = queryEngine.getUser(accessTokenResponse);
      OAuthNotifyIdentifier identifier = queryEngine.getIdentifier(accessTokenResponse);

      String email = user.getEmail();
      String phone = null;

      if(identifier != null) {
         if(StringUtils.isEmpty(email)) {
            email = identifier.getEmail();
         }

         phone = identifier.getPhone();
      }

      UserType userType = queryEngine.getUserType();
      // using enum's name, the label just using display type.
      String account = userType.name() + ":" + user.getAccount();

      Customer customer = customerService.getCustomerByAccount(account);

      if(customer == null) {
         // sign up
         customer = new Customer();
         customer.setAccount(account);
         customer.setName(user.getName());
         customer.setEmail(email);
         customer.setPhone(phone);
         customer.setGender(Gender.Unknown);
         customer.setActive(true);
         customer.setType(userType);
         customer.setRegisterDate(new Date());
         // password is not required.
         // TODO registered user should have default role.
//         customer.setRoles();

         try{
            customerService.insertCustomer(customer);
         }
         catch(Exception e) {
            LOGGER.error(I18nUtil.getString("user.errorMsg.insertError"));
            throw e;
         }
      }

      // login
      authentication(customer);
   }

   private void authentication(Customer customer) {
      Subject subject = SecurityUtils.getSubject();

      subject.login(new OAuthUsernamePasswordToken(
         customer.getAccount(), "", customer.getType()));
   }

   private final CustomerService customerService;
   private final QueryEngineFactory engineFactory;
   private static final Logger LOGGER = LoggerFactory.getLogger(OAuthLoginController.class);
}
