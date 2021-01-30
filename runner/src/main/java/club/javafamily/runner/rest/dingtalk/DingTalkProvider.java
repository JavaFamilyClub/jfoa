/*
 * Copyright (c) 2021, JavaFamily Technology Corp, All Rights Reserved.
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

package club.javafamily.runner.rest.dingtalk;

import club.javafamily.commons.enums.UserType;
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
@Service("dingTalkProvider")
public class DingTalkProvider implements QueryEngine<DingTalkUser> {

   @Autowired
   public DingTalkProvider(RestTemplate restTemplate,
                           OAuthProperties oAuthProperties)
   {
      this.restTemplate = restTemplate;
      this.oAuthProperties = oAuthProperties;
   }

   @Override
   public UserType getUserType() {
      return UserType.DingTalk;
   }

   @Override
   public BaseOAuthProperties getProps() {
      return this.oAuthProperties.getDingTalk();
   }

   @Override
   public Map<String, String> getAuthorizeParams() {
      Map<String, String> params = new HashMap<>();

      params.put("appid", getProps().getClientId());
      params.put("scope", "snsapi_login");
      params.put("response_type", "code");
      params.put("state", getUserType().name());
      params.put("redirect_uri", oAuthProperties.getCallback());

      return params;
   }

   @Override
   public RestTemplate getRestTemplate() {
      return this.restTemplate;
   }

   @Override
   public String getAccessTokenUrl() {
      return null;
   }

   @Override
   public String getUserInfoUrl() {
      return null;
   }

   @Override
   public String getEmailUrl() {
      return null;
   }

   @Override
   public Class<DingTalkUser> getUserClass() {
      return null;
   }

   @Override
   public AccessTokenDTO buildAccessTokenDTO(String code, String state) {
      return null;
   }

   @Override
   public OAuthNotifyIdentifier getIdentifier(AccessTokenResponse accessTokenResponse) {
      return null;
   }

   private final RestTemplate restTemplate;
   private final OAuthProperties oAuthProperties;
}
