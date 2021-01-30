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

import club.javafamily.commons.enums.UserType;
import club.javafamily.runner.controller.model.OAuthAuthenticationException;
import club.javafamily.runner.dto.*;
import club.javafamily.runner.properties.BaseOAuthProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.util.StringUtils;
import org.springframework.web.client.*;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

public interface QueryEngine <T extends RestUser> {

   UserType getUserType();

   /**
    * match queryEngine predicate
    */
   default boolean isAccept(UserType userType) {
      return getUserType() == userType;
   }

   /**
    * Getting properties config.
    */
   BaseOAuthProperties getProps();

   /**
    * Getting params of Authorize redirect url.
    */
   Map<String, String> getAuthorizeParams();

   /**
    * Build authorize url.
    */
   default String getAuthorizeUrl() {
      Map<String, String> params = getAuthorizeParams();
      StringBuilder sb = new StringBuilder();

      sb.append(getProps().getUrl());
      sb.append("?");

      String paramsStr = params == null ? ""
         : params.entrySet().stream()
         .map(kv -> kv.getKey() + "=" + kv.getValue())
         .collect(Collectors.joining("&"));

      sb.append(paramsStr);

      return sb.toString();
   }

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
    * fetch email url
    */
   String getEmailUrl();

   /**
    * query access token param name
    */
   default String authorizationParamName() {
      return "Authorization";
   }

   /**
    * User class type.
    */
   Class<T> getUserClass();

   /**
    * execute get query for setting header.
    * @param returnType response type
    * @param url url
    * @param headers query header
    */
   default <R> R getForObject(String url,
                              Class<R> returnType,
                              HttpHeaders headers)
   {
      RestTemplate restTemplate = getRestTemplate();
      headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
      headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

      HttpEntity<R> request = new HttpEntity<>(headers);
      RequestCallback requestCallback = restTemplate.httpEntityCallback(request, returnType);
      HttpMessageConverterExtractor<R> responseExtractor =
         new HttpMessageConverterExtractor<>(returnType,
            restTemplate.getMessageConverters());

      return restTemplate.execute(url, HttpMethod.GET,
         requestCallback, responseExtractor);
   }

   AccessTokenDTO buildAccessTokenDTO(String code, String state);

   default AccessTokenResponse queryAccessToken(String code, String state) {
      AccessTokenDTO accessTokenDTO = buildAccessTokenDTO(code, state);

      return queryAccessToken(accessTokenDTO);
   }

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

   default T getUser(AccessTokenResponse accessTokenResponse) {
      String url = getUserInfoUrl();
      HttpHeaders headers = queryResourceHeader(accessTokenResponse);

      try{
         return getForObject(url, getUserClass(), headers);
      }
      catch(Exception e) {
         e.printStackTrace();
         throw new OAuthAuthenticationException("OAuth Authentication Getting User Failed.");
      }
   }

   /**
    * OAuth getting user identifier
    */
   OAuthNotifyIdentifier getIdentifier(AccessTokenResponse accessTokenResponse);

   default <R> R getEmail(AccessTokenResponse accessTokenResponse,
                          Class<R> clazz)
   {
      String url = getEmailUrl();
      HttpHeaders headers = queryResourceHeader(accessTokenResponse);

      try{
         return getForObject(url, clazz, headers);
      }
      catch(Exception e) {
         e.printStackTrace();
         throw new OAuthAuthenticationException("OAuth Authentication Getting User Email Failed.");
      }
   }

   default HttpHeaders queryResourceHeader(AccessTokenResponse token) {
      HttpHeaders headers = new HttpHeaders();
      headers.set(authorizationParamName(),
         token.getToken_type()
            + " " + token.getAccess_token());

      return headers;
   }

   Logger LOGGER = LoggerFactory.getLogger(QueryEngine.class);
}
