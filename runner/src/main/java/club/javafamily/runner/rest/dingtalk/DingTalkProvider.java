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
import club.javafamily.runner.controller.model.OAuthAuthenticationException;
import club.javafamily.runner.properties.BaseOAuthProperties;
import club.javafamily.runner.properties.OAuthProperties;
import club.javafamily.runner.rest.QueryEngine;
import club.javafamily.runner.rest.dto.*;
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
   public Class<? extends AccessTokenResponse> accessTokenResponse() {
      return DingTalkAccessTokenResponse.class;
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
      String uri = "https://oapi.dingtalk.com/sns/gettoken";

      uri += ("?appid=" + getProps().getClientId());
      uri += ("&appsecret=" + getProps().getClientSecrets());

      return uri;
   }

   @Override
   public AccessTokenResponse accessTokenPostProcessor(AccessTokenDTO accessTokenDTO,
                                                       AccessTokenResponse accessTokenResponse)
   {
      String uri = "https://oapi.dingtalk.com/sns/get_persistent_code?access_token="
         + accessTokenResponse.getAccess_token();

      TmpAuthResponse ampAuthResponse = postForObject(
         uri, new TmpAuthCode(accessTokenDTO.getCode()), TmpAuthResponse.class);

      // getting sns_token
      uri = "https://oapi.dingtalk.com/sns/get_sns_token?access_token="
         + accessTokenResponse.getAccess_token();

      DingTalkSnsTokenResponse snsTokenResponse = postForObject(
         uri, ampAuthResponse, DingTalkSnsTokenResponse.class);

      accessTokenResponse.setAccess_token(snsTokenResponse.getSns_token());

      return accessTokenResponse;
   }

   @Override
   public String getUserInfoUrl(AccessTokenResponse accessTokenResponse) {
      return "https://oapi.dingtalk.com/sns/getuserinfo?sns_token="
         + accessTokenResponse.getAccess_token();
   }

   @Override
   public String getEmailUrl() {
      return null;
   }

   @Override
   public Class<DingTalkUser> getUserClass() {
      return DingTalkUser.class;
   }

   @Override
   public AccessTokenDTO buildAccessTokenDTO(String code, String state) {
      if(!UserType.DingTalk.name().equals(state)) {
         throw new OAuthAuthenticationException("OAuth Authentication State is not match: " + state);
      }

      DingTalkAccessTokenDTO accessTokenDTO = new DingTalkAccessTokenDTO();
      accessTokenDTO.setAppid(getProps().getClientId());
      accessTokenDTO.setAppsecret(getProps().getClientSecrets());
      accessTokenDTO.setCode(code);
      accessTokenDTO.setState(state);

      return accessTokenDTO;
   }

   @Override
   public OAuthNotifyIdentifier getIdentifier(AccessTokenResponse accessTokenResponse) {
      return null;
   }

   private final RestTemplate restTemplate;
   private final OAuthProperties oAuthProperties;
}
