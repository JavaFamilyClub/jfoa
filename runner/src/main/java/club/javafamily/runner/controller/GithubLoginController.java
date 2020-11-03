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
import club.javafamily.runner.service.CustomerService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
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

   private String callback = "http://localhost/public/oauth/github/callback";

   @Autowired
   public GithubLoginController(CustomerService customerService, RestTemplate restTemplate) {
      this.customerService = customerService;
      this.restTemplate = restTemplate;
   }

   @GetMapping("/public/oauth/github/auth")
   public String auth() {
      StringBuilder sb = new StringBuilder();

      sb.append("https://github.com/login/oauth/authorize?client_id=");
      sb.append(clientId);
      sb.append("&redirect_uri=");
      sb.append(callback);
      sb.append("&scope=user:email&response_type=code&state=1");

      return "redirect:" + sb.toString();
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

      AccessTokenResponse accessTokenResponse = getAccessToken(accessTokenDTO);

      System.out.println("Getting access token: " + accessTokenResponse);

      if(accessTokenResponse == null || StringUtils.isEmpty(accessTokenResponse.getAccess_token())) {
         // TODO i18n
         throw new OAuthAuthenticationException("OAuth Authentication Failed.");
      }

      // TODO registered user and login.
      authentication(accessTokenResponse);

      return "redirect:/";
   }

   private void authentication(AccessTokenResponse accessTokenResponse) {
      GithubUser user = getUser(accessTokenResponse);
      String email = user.getEmail();
      String account = JF_GITHUB_USER_ACCOUNT_PREFIX + user.getLogin();

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

   private GithubUser getUser(AccessTokenResponse accessTokenResponse) {
      Map<String, String> params = new HashMap<>();

      params.put("access_token", accessTokenResponse.getAccess_token());

      try{
         GithubUser githubUser = restTemplate.getForObject(
            "https://api.github.com/user", GithubUser.class, params);

         LOGGER.info("Getting github user info: " + githubUser);

         return githubUser;
      }
      catch(Exception ignore) {
      }

      // TODO i18n
      throw new OAuthAuthenticationException("OAuth Authentication Getting User Failed.");
   }

   private AccessTokenResponse getAccessToken(AccessTokenDTO accessTokenDTO) {
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
      HttpEntity<AccessTokenDTO> requestBody = new HttpEntity<>(accessTokenDTO, headers);

      AccessTokenResponse accessTokenResponse = restTemplate.postForObject(
         "https://github.com/login/oauth/access_token",
         requestBody, AccessTokenResponse.class);

      return accessTokenResponse;
   }

   private static final String JF_GITHUB_USER_ACCOUNT_PREFIX = "JF_GITHUB_^_^_";

   private final CustomerService customerService;
   private final RestTemplate restTemplate;
   private static final Logger LOGGER = LoggerFactory.getLogger(GithubLoginController.class);
}
