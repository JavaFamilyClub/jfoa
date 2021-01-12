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

package club.javafamily.runner.rest.github;

import club.javafamily.runner.controller.model.OAuthAuthenticationException;
import club.javafamily.runner.dto.*;
import club.javafamily.runner.properties.OAuthProperties;
import club.javafamily.runner.rest.QueryEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Lazy
@Service("githubProvider")
public class GithubProvider implements QueryEngine<GithubUser> {

   private static final String AUTHORIZE_URL = "https://github.com/login/oauth/authorize";

   @Autowired
   public GithubProvider(RestTemplate restTemplate,
                         OAuthProperties oAuthProperties)
   {
      this.restTemplate = restTemplate;
      this.oAuthProperties = oAuthProperties;
   }

   public String getAuthorizeUrl() {
      assert oAuthProperties.getGithub() != null;
      Map<String, String> params = new HashMap<>();
      params.put("client_id", oAuthProperties.getGithub().getClientId());
      params.put("redirect_uri", oAuthProperties.getCallback());
      params.put("scope", "user:email");
      params.put("response_type", "code");
      params.put("state", "1"); // TODO generate state

      return this.getAuthorizeUrl(params);
   }

   public String getAuthorizeUrl(Map<String, String> params) {
      StringBuilder sb = new StringBuilder();

      sb.append(AUTHORIZE_URL);
      sb.append("?");

      String paramsStr = params.entrySet().stream()
         .map(kv -> kv.getKey() + "=" + kv.getValue())
         .collect(Collectors.joining("&"));

      sb.append(paramsStr);

      return sb.toString();
   }

   public AccessTokenResponse queryAccessToken(String code, String state) {
      assert oAuthProperties.getGithub() != null;

      if(!"1".equals(state)) {
         throw new OAuthAuthenticationException("OAuth Authentication State is not match.");
      }

      AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
      accessTokenDTO.setClient_id(this.oAuthProperties.getGithub().getClientId());
      accessTokenDTO.setClient_secret(this.oAuthProperties.getGithub().getClientSecrets());
      accessTokenDTO.setCode(code);
      accessTokenDTO.setState(state);
      accessTokenDTO.setRedirect_uri(oAuthProperties.getCallback());

      return queryAccessToken(accessTokenDTO);
   }

   @Override
   public RestTemplate getRestTemplate() {
      return restTemplate;
   }

   @Override
   public String getAccessTokenUrl() {
      return "https://github.com/login/oauth/access_token";
   }

   @Override
   public String getUserInfoUrl() {
      return "https://api.github.com/user";
   }

   @Override
   public String getEmailUrl() {
      return "https://api.github.com/user/emails";
   }

   @Override
   public Class<GithubUser> getUserClass() {
      return GithubUser.class;
   }

   @Override
   public OAuthNotifyIdentifier getIdentifier(AccessTokenResponse accessTokenResponse) {
      GitHubEmailResponse result = null;
      GitHubEmailResponse[] emails = getEmail(accessTokenResponse, GitHubEmailResponse[].class);

      if(emails != null) {
         for(GitHubEmailResponse email : emails) {
            // make sure at least one email address has selected.
            if((result == null && (email.isVerified() || email.isPrimary()))) {
               result = email;
            }
            else if(email.isVerified() && email.isPrimary()) {
               result = email; // found major.
               break;
            }
         }
      }

      return result;
   }

   private final RestTemplate restTemplate;
   private final OAuthProperties oAuthProperties;
}
