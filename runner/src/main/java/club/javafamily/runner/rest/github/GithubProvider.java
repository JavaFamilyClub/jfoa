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

import club.javafamily.commons.enums.UserType;
import club.javafamily.runner.controller.model.OAuthAuthenticationException;
import club.javafamily.runner.dto.*;
import club.javafamily.runner.properties.BaseOAuthProperties;
import club.javafamily.runner.properties.OAuthProperties;
import club.javafamily.runner.rest.QueryEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Lazy
@Service("githubProvider")
public class GithubProvider implements QueryEngine<GithubUser> {

   @Autowired
   public GithubProvider(RestTemplate restTemplate,
                         OAuthProperties oAuthProperties)
   {
      this.restTemplate = restTemplate;
      this.oAuthProperties = oAuthProperties;
   }

   private OAuthProperties.GithubOAuthProperties githubProp() {
      return oAuthProperties.getGithub();
   }

   @Override
   public BaseOAuthProperties getProps() {
      return githubProp();
   }

   @Override
   public Map<String, String> getAuthorizeParams() {
      Map<String, String> params = new HashMap<>();
      params.put("client_id", githubProp().getClientId());
      params.put("redirect_uri", oAuthProperties.getCallback());
      params.put("scope", "user:email");
      params.put("response_type", "code");
      params.put("state", getUserType().name());

      return params;
   }

   @Override
   public AccessTokenDTO buildAccessTokenDTO(String code, String state) {
      assert oAuthProperties.getGithub() != null;

      if(!UserType.GitHub.name().equals(state)) {
         throw new OAuthAuthenticationException("OAuth Authentication State is not match: " + state);
      }

      AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
      accessTokenDTO.setClient_id(githubProp().getClientId());
      accessTokenDTO.setClient_secret(githubProp().getClientSecrets());
      accessTokenDTO.setCode(code);
      accessTokenDTO.setState(state);
      accessTokenDTO.setRedirect_uri(oAuthProperties.getCallback());

      return accessTokenDTO;
   }

   @Override
   public UserType getUserType() {
      return UserType.GitHub;
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
