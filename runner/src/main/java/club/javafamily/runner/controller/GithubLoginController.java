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
import club.javafamily.runner.config.OAuthUsernamePasswordToken;
import club.javafamily.runner.domain.Customer;
import club.javafamily.runner.dto.AccessTokenResponse;
import club.javafamily.runner.dto.GithubUser;
import club.javafamily.runner.rest.github.GithubProvider;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Controller
@Lazy
public class GithubLoginController {

   @Autowired
   public GithubLoginController(CustomerService customerService,
                                GithubProvider githubProvider)
   {
      this.customerService = customerService;
      this.githubProvider = githubProvider;
   }

   @GetMapping("/public/oauth/github/auth")
   public String auth() {
      String authorizeUrl = githubProvider.getAuthorizeUrl();

      return "redirect:" + authorizeUrl;
   }

   @GetMapping("/public/oauth/github/callback")
   public String callback(@RequestParam("code") String code,
                          @RequestParam("state") String state,
                          HttpServletRequest request)
   {
      AccessTokenResponse accessTokenResponse
         = githubProvider.queryAccessToken(code, state);

      authentication(accessTokenResponse);

      return WebMvcUtil.redirectOrElse(request, "/");
   }

   private void authentication(AccessTokenResponse accessTokenResponse) {
      if(accessTokenResponse == null) {
         LOGGER.error("AccessTokenResponse is null.");
         return;
      }

      GithubUser user = githubProvider.getUser(accessTokenResponse);

      String email = user.getEmail();
      String account = UserType.GitHub.getLabel() + ":" + user.getLogin();

      Customer customer = customerService.getCustomerByAccount(account);

      if(customer == null) {
         // sign up
         customer = new Customer();
         customer.setAccount(account);
         customer.setName(user.getName());
         customer.setEmail(email);
         customer.setGender(Gender.Unknown);
         customer.setActive(true);
         customer.setType(UserType.GitHub);
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
   private final GithubProvider githubProvider;
   private static final Logger LOGGER = LoggerFactory.getLogger(GithubLoginController.class);
}
