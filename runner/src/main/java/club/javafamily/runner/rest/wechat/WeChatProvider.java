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

package club.javafamily.runner.rest.wechat;

import club.javafamily.runner.dto.AccessTokenResponse;
import club.javafamily.runner.dto.WeChatUser;
import club.javafamily.runner.rest.QueryEngine;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Lazy
@Service("weChatProvider")
public class WeChatProvider implements QueryEngine<WeChatUser> {

   @Override
   public RestTemplate getRestTemplate() {
      return null;
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
   public Class<WeChatUser> getUserClass() {
      return WeChatUser.class;
   }

   @Override
   public <R> R getEmail(AccessTokenResponse accessTokenResponse) {
      return null;
   }
}
