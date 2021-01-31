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
import club.javafamily.runner.rest.dto.RestUser;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DingTalkUser implements RestUser {
   private String errcode;
   private String errmsg;
   @JsonProperty(value = "user_info")
   private DingTalkUserInfo user_info;

   @Override
   public String getName() {
      return user_info.getNick();
   }

   @Override
   public String getAccount() {
      return user_info.getDingId();
   }

   @Override
   public String getEmail() {
      return null;
   }

   @Override
   public UserType getUserType() {
      return UserType.DingTalk;
   }

}
