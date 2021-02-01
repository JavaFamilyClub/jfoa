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

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class DingUserSnsInfo implements Serializable {
   private String errcode;
   private String errmsg;
   @JsonProperty(value = "user_info")
   private DingTalkUserInfo user_info;

   public String getErrcode() {
      return errcode;
   }

   public void setErrcode(String errcode) {
      this.errcode = errcode;
   }

   public String getErrmsg() {
      return errmsg;
   }

   public void setErrmsg(String errmsg) {
      this.errmsg = errmsg;
   }

   public DingTalkUserInfo getUser_info() {
      return user_info;
   }

   public void setUser_info(DingTalkUserInfo user_info) {
      this.user_info = user_info;
   }

   public String unionid() {
      return user_info.getUnionid();
   }
}
