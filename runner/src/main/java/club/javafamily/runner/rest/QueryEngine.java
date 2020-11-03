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

package club.javafamily.runner.rest;

import club.javafamily.runner.controller.model.OAuthAuthenticationException;
import club.javafamily.runner.dto.*;
import org.springframework.http.*;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

public interface QueryEngine <T extends RestUser> {

   /**
    * query driver
    */
   RestTemplate getRestTemplate();

   /**
    * fetch access token url
    */
   String getAccessTokenUrl();

   /**
    * fetch user info url
    */
   String getUserInfoUrl();

   /**
    * query access token param name
    */
   default String accessTokeParamName() {
      return "access_token";
   }

   /**
    * User class type.
    */
   Class<T> getUserClass();

   /**
    * fetch access token
    */
   default AccessTokenResponse queryAccessToken(AccessTokenDTO accessTokenDTO) {
      RestTemplate restTemplate = getRestTemplate();

      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
      HttpEntity<AccessTokenDTO> requestBody = new HttpEntity<>(accessTokenDTO, headers);

      AccessTokenResponse accessTokenResponse = restTemplate.postForObject(
         getAccessTokenUrl(), requestBody, AccessTokenResponse.class);

      if(accessTokenResponse == null
         || StringUtils.isEmpty(accessTokenResponse.getAccess_token()))
      {
         throw new OAuthAuthenticationException("OAuth Authentication Failed.");
      }

      return accessTokenResponse;
   }

   default T getUser(String accessToken) {
      RestTemplate restTemplate = getRestTemplate();
      Map<String, String> params = new HashMap<>();

      params.put(accessTokeParamName(), accessToken);

      try{
         T user = restTemplate.getForObject(getUserInfoUrl(), getUserClass(), params);

         return user;
      }
      catch(Exception ignore) {
      }

      throw new OAuthAuthenticationException("OAuth Authentication Getting User Failed.");
   }
}
