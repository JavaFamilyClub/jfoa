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
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.*;
import com.dingtalk.api.response.*;
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
//      String uri = "https://oapi.dingtalk.com/sns/get_persistent_code?access_token="
//         + accessTokenResponse.getAccess_token();
//
//      TmpAuthResponse ampAuthResponse = postForObject(
//         uri, new TmpAuthCode(accessTokenDTO.getCode()), TmpAuthResponse.class);
//
//      // getting sns_token
//      uri = "https://oapi.dingtalk.com/sns/get_sns_token?access_token="
//         + accessTokenResponse.getAccess_token();
//
//      DingTalkSnsTokenResponse snsTokenResponse = postForObject(
//         uri, ampAuthResponse, DingTalkSnsTokenResponse.class);

//      accessTokenResponse.setAccess_token(snsTokenResponse.getSns_token());

      return accessTokenResponse;
   }

   @Override
   public String getUserInfoUrl(AccessTokenResponse accessTokenResponse) throws Exception {
//      String snsUnionidUri = "https://oapi.dingtalk.com/sns/getuserinfo?sns_token="
//         + accessTokenResponse.getAccess_token();
//
//      DingUserSnsInfo snsInfo = getForObject(
//         snsUnionidUri, DingUserSnsInfo.class, accessTokenResponse);
//
//      String unionid = snsInfo.unionid();
//
//      String unionidAccessTokenUri = "https://oapi.dingtalk.com/gettoken" +
//         "?appkey=ding63jbqyuf4kprisez" +
//         "&appsecret=pEryLKS7UgVXH-ZSat5bk0oez7Z70OvNZ2DpmMVbbLHUdJ_-l3zU0rPM3TqVnGmZ";
//
//      AccessTokenResponse unionidAccessTokenResponse = getForObject(
//         unionidAccessTokenUri, accessTokenResponse(), getBaseHttpHeaders());
//
//      String userIdUri = "https://oapi.dingtalk.com/topapi/user/getbyunionid?access_token="
//         + unionidAccessTokenResponse.getAccess_token();
//
//      DingGetUserIdResponse userIdResponse = postForObject(
//         userIdUri, new UnionidObject(unionid), DingGetUserIdResponse.class);

      // query params: lang (default: zh_CN)
//      return "https://oapi.dingtalk.com/user/get?access_token="
//         + accessTokenResponse.getAccess_token()
//         + "&userid=" + userIdResponse.getUserid();

      DefaultDingTalkClient client2 = new DefaultDingTalkClient("https://oapi.dingtalk.com/sns/getuserinfo_bycode");
      OapiSnsGetuserinfoBycodeRequest reqBycodeRequest = new OapiSnsGetuserinfoBycodeRequest();
      // 通过扫描二维码，跳转指定的redirect_uri后，向url中追加的code临时授权码
      reqBycodeRequest.setTmpAuthCode(accessTokenResponse.getAccessTokenDTO().getCode());
      OapiSnsGetuserinfoBycodeResponse bycodeResponse
         = client2.execute(reqBycodeRequest, getProps().getClientId(), getProps().getClientSecrets());

      DefaultDingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/service/get_corp_token");
      OapiServiceGetCorpTokenRequest req = new OapiServiceGetCorpTokenRequest();
      req.setAuthCorpid("ding9c92b8c37659ccaef5bf40eda33b7ba0");
      OapiServiceGetCorpTokenResponse execute = client.execute(req, "ding63jbqyuf4kprisez", "pEryLKS7UgVXH-ZSat5bk0oez7Z70OvNZ2DpmMVbbLHUdJ_-l3zU0rPM3TqVnGmZ", "suiteTicket");


      // 根据unionid获取userid
      String unionid = bycodeResponse.getUserInfo().getUnionid();
      DingTalkClient clientDingTalkClient = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/user/getbyunionid");
      OapiUserGetbyunionidRequest reqGetbyunionidRequest = new OapiUserGetbyunionidRequest();
      reqGetbyunionidRequest.setUnionid(unionid);
      OapiUserGetbyunionidResponse oapiUserGetbyunionidResponse
         = clientDingTalkClient.execute(reqGetbyunionidRequest, accessTokenResponse.getAccess_token());

      // query params: lang (default: zh_CN)
      return "https://oapi.dingtalk.com/user/get?access_token="
         + accessTokenResponse.getAccess_token()
         + "&userid=" + oapiUserGetbyunionidResponse.getResult().getUserid();
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
