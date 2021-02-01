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

public class DingGetUserIdResponse implements Serializable {
   private String errcode;
   private String errmsg;
   @JsonProperty(value = "result")
   private DingGetUserIdResult result;

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

   public DingGetUserIdResult getResult() {
      return result;
   }

   public void setResult(DingGetUserIdResult result) {
      this.result = result;
   }

   public String getUserid() {
      return result.userid;
   }

   static class DingGetUserIdResult implements Serializable {
      private int contactType; // 0: 表示企业内部员工, 1: 表示企业外部联系人
      private String userid;

      public int getContactType() {
         return contactType;
      }

      public void setContactType(int contactType) {
         this.contactType = contactType;
      }

      public String getUserid() {
         return userid;
      }

      public void setUserid(String userid) {
         this.userid = userid;
      }
   }
}
