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

import club.javafamily.runner.dto.AccessTokenDTO;
import club.javafamily.runner.dto.AccessTokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

@Controller
@Lazy
public class GithubLoginController {

   @Value("${jfoa.rest.github.client-id}")
   private String clientId;

   @Value("${jfoa.rest.github.client-secrets}")
   private String clientSecrets;

   private String callback = "http://localhost/public/oauth/github/callback";

   @Autowired
   public GithubLoginController(RestTemplate restTemplate) {
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

      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
      HttpEntity<AccessTokenDTO> requestBody = new HttpEntity<>(accessTokenDTO, headers);

      AccessTokenResponse accessTokenResponse = restTemplate.postForObject(
         "https://github.com/login/oauth/access_token",
         requestBody, AccessTokenResponse.class);

      System.out.println("Getting access token: " + accessTokenResponse);

      // TODO registered user and login.

      return "redirect:/";
   }


   private final RestTemplate restTemplate;
}
