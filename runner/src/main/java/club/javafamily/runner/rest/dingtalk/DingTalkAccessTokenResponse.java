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

import club.javafamily.runner.rest.dto.AccessTokenResponse;

public class DingTalkAccessTokenResponse extends AccessTokenResponse {
   private String errcode;
   private String errmsg;
   private String expires_in;

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

   public String getExpires_in() {
      return expires_in;
   }

   public void setExpires_in(String expires_in) {
      this.expires_in = expires_in;
   }
}
