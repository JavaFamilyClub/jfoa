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

public class TmpAuthResponse implements Serializable {
   private String errcode;
   private String errmsg;
   private String unionid;
   private String openid;
   private String persistent_code;

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

   public String getUnionid() {
      return unionid;
   }

   public void setUnionid(String unionid) {
      this.unionid = unionid;
   }

   public String getOpenid() {
      return openid;
   }

   public void setOpenid(String openid) {
      this.openid = openid;
   }

   public String getPersistent_code() {
      return persistent_code;
   }

   public void setPersistent_code(String persistent_code) {
      this.persistent_code = persistent_code;
   }
}
