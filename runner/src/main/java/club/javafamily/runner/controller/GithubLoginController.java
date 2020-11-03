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

import club.javafamily.runner.config.OAuthUsernamePasswordToken;
import club.javafamily.runner.controller.model.OAuthAuthenticationException;
import club.javafamily.runner.domain.Customer;
import club.javafamily.runner.dto.*;
import club.javafamily.runner.enums.Gender;
import club.javafamily.runner.enums.UserType;
import club.javafamily.runner.rest.github.GithubProvider;
import club.javafamily.runner.service.CustomerService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Controller
@Lazy
public class GithubLoginController {

   @Value("${jfoa.rest.github.client-id}")
   private String clientId;

   @Value("${jfoa.rest.github.client-secrets}")
   private String clientSecrets;

   @Value("${jfoa.rest.callback}")
   private String callback;

   @Autowired
   public GithubLoginController(CustomerService customerService,
                                GithubProvider githubProvider)
   {
      this.customerService = customerService;
      this.githubProvider = githubProvider;
   }

   @GetMapping("/public/oauth/github/auth")
   public String auth() {
      Map<String, String> params = new HashMap<>();
      params.put("client_id", clientId);
      params.put("redirect_uri", callback);
      params.put("scope", "user:email");
      params.put("response_type", "code");
      params.put("state", "1");

      String authorizeUrl = githubProvider.getAuthorizeUrl(params);

      return "redirect:" + authorizeUrl;
   }

   @GetMapping("/public/oauth/github/callback")
   public String callback(@RequestParam("code") String code,
                          @RequestParam("state") String state)
   {
      AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
      accessTokenDTO.setClient_id(this.clientId);
      accessTokenDTO.setClient_secret(this.clientSecrets);
      accessTokenDTO.setCode(code);
      accessTokenDTO.setState(state);
      accessTokenDTO.setRedirect_uri(callback);

      AccessTokenResponse accessTokenResponse = githubProvider.queryAccessToken(accessTokenDTO);

      LOGGER.info("Getting access token: {}", accessTokenResponse);

      authentication(accessTokenResponse);

      return "redirect:/";
   }

   private void authentication(AccessTokenResponse accessTokenResponse) {
      GithubUser user = githubProvider.getUser(accessTokenResponse.getAccess_token());
      String email = user.getEmail();
      String account = user.getLogin();

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
         // password is not required.
         // TODO registered user should have default role.
//         customer.setRoles();
         customerService.insertCustomer(customer);
      }

      // login
      authentication(customer);
   }

   private void authentication(Customer customer) {
      Subject subject = SecurityUtils.getSubject();

      subject.login(new OAuthUsernamePasswordToken(
         customer.getAccount(), null, customer.getType()));
   }

   private final CustomerService customerService;
   private final GithubProvider githubProvider;
   private static final Logger LOGGER = LoggerFactory.getLogger(GithubLoginController.class);
}
