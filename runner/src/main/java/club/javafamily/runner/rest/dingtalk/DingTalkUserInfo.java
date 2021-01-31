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

import java.io.Serializable;

public class DingTalkUserInfo implements Serializable {
   private String nick;
   private String unionid;
   private String dingId;
   private String openid;

   public String getNick() {
      return nick;
   }

   public void setNick(String nick) {
      this.nick = nick;
   }

   public String getUnionid() {
      return unionid;
   }

   public void setUnionid(String unionid) {
      this.unionid = unionid;
   }

   public String getDingId() {
      return dingId;
   }

   public void setDingId(String dingId) {
      this.dingId = dingId;
   }

   public String getOpenid() {
      return openid;
   }

   public void setOpenid(String openid) {
      this.openid = openid;
   }
}
