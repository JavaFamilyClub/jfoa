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

package club.javafamily.runner.web.security.model;

import club.javafamily.commons.enums.UserType;

public class JfPrincipal {

   public JfPrincipal() {
   }

   public String getUserName() {
      return userName;
   }

   public void setUserName(String userName) {
      this.userName = userName;
   }

   public boolean isAuthenticated() {
      return authenticated;
   }

   public void setAuthenticated(boolean authenticated) {
      this.authenticated = authenticated;
   }

   public String getEmail() {
      return email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   public int getId() {
      return id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public String getAccount() {
      return account;
   }

   public void setAccount(String account) {
      this.account = account;
   }

   public UserType getType() {
      return type;
   }

   public void setType(UserType type) {
      this.type = type;
   }

   private boolean authenticated;
   private int id;
   private String account;
   private String userName;
   private String email;
   private UserType type;
}
