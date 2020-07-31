/*
 * Copyright (c) 2019, JavaFamily Technology Corp, All Rights Reserved.
 *
 * The software and information contained herein are copyrighted and
 * proprietary to AngBoot Technology Corp. This software is furnished
 * pursuant to a written license agreement and may be used, copied,
 * transmitted, and stored only in accordance with the terms of such
 * license and with the inclusion of the above copyright notice. Please
 * refer to the file "COPYRIGHT" for further copyright and licensing
 * information. This software and information or any other copies
 * thereof may not be provided or otherwise made available to any other
 * person.
 */

package club.javafamily.runner.common.model.amqp;

import java.io.Serializable;

public class RegisterUserInfo implements Serializable {
   private String account;
   private String userName;
   private String password;
   private String verifyBaseLink;

   public String getAccount() {
      return account;
   }

   public void setAccount(String account) {
      this.account = account;
   }

   public String getUserName() {
      return userName;
   }

   public void setUserName(String userName) {
      this.userName = userName;
   }

   public String getPassword() {
      return password;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public String getVerifyBaseLink() {
      return verifyBaseLink;
   }

   public void setVerifyBaseLink(String verifyBaseLink) {
      this.verifyBaseLink = verifyBaseLink;
   }

   @Override
   public String toString() {
      return "RegisterUserInfo{" +
         "account='" + account + '\'' +
         ", userName='" + userName + '\'' +
         ", password='" + password + '\'' +
         ", verifyBaseLink='" + verifyBaseLink + '\'' +
         '}';
   }
}
