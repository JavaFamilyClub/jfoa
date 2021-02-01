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

public class DingTalkUser implements RestUser {
   private String errcode;
   private String unionid;
   private String userid;
   private boolean isBoss;
   private String tel;
   private String workPlace;
   private String orgEmail;
   private String email;
   private String mobile;
   private String errmsg;
   private boolean active;
   private boolean isAdmin;
   private String name;
   private String position;
   private boolean realAuthed;

   @Override
   public String getName() {
      return name;
   }

   @Override
   public String getAccount() {
      return userid;
   }

   @Override
   public String getEmail() {
      return email;
   }

   @Override
   public UserType getUserType() {
      return UserType.DingTalk;
   }

   public String getErrcode() {
      return errcode;
   }

   public void setErrcode(String errcode) {
      this.errcode = errcode;
   }

   public String getUnionid() {
      return unionid;
   }

   public void setUnionid(String unionid) {
      this.unionid = unionid;
   }

   public String getUserid() {
      return userid;
   }

   public void setUserid(String userid) {
      this.userid = userid;
   }

   public boolean isBoss() {
      return isBoss;
   }

   public void setBoss(boolean boss) {
      isBoss = boss;
   }

   public String getTel() {
      return tel;
   }

   public void setTel(String tel) {
      this.tel = tel;
   }

   public String getWorkPlace() {
      return workPlace;
   }

   public void setWorkPlace(String workPlace) {
      this.workPlace = workPlace;
   }

   public String getOrgEmail() {
      return orgEmail;
   }

   public void setOrgEmail(String orgEmail) {
      this.orgEmail = orgEmail;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   public String getMobile() {
      return mobile;
   }

   public void setMobile(String mobile) {
      this.mobile = mobile;
   }

   public String getErrmsg() {
      return errmsg;
   }

   public void setErrmsg(String errmsg) {
      this.errmsg = errmsg;
   }

   public boolean isActive() {
      return active;
   }

   public void setActive(boolean active) {
      this.active = active;
   }

   public boolean isAdmin() {
      return isAdmin;
   }

   public void setAdmin(boolean admin) {
      isAdmin = admin;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getPosition() {
      return position;
   }

   public void setPosition(String position) {
      this.position = position;
   }

   public boolean isRealAuthed() {
      return realAuthed;
   }

   public void setRealAuthed(boolean realAuthed) {
      this.realAuthed = realAuthed;
   }
}
