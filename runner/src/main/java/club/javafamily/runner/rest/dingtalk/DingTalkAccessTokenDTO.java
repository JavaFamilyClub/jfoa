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

import club.javafamily.runner.rest.dto.AccessTokenDTO;

public class DingTalkAccessTokenDTO extends AccessTokenDTO {
   private String appid;
   private String appsecret;

   public String getAppid() {
      return appid;
   }

   public void setAppid(String appid) {
      this.appid = appid;
   }

   public String getAppsecret() {
      return appsecret;
   }

   public void setAppsecret(String appsecret) {
      this.appsecret = appsecret;
   }
}
