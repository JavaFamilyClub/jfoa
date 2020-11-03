/*
 * Copyright (c) 2020, JavaFamily Technology Corp, All Rights Reserved.
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

package club.javafamily.runner.dto;

import club.javafamily.runner.enums.Gender;
import club.javafamily.runner.enums.UserType;

public class GithubUser implements RestUser {
   private String name; // nick name
   private Long id; // long id
   private String bio; // desc
   private String login; // account
   private String email; // email
   private String company; // company
   private String location; // location

   public String getName() {
      return name;
   }

   @Override
   public String getAccount() {
      return getLogin();
   }

   public void setName(String name) {
      this.name = name;
   }

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public String getBio() {
      return bio;
   }

   public void setBio(String bio) {
      this.bio = bio;
   }

   public String getLogin() {
      return login;
   }

   public void setLogin(String login) {
      this.login = login;
   }

   @Override
   public String getEmail() {
      return email;
   }

   @Override
   public UserType getUserType() {
      return UserType.GitHub;
   }

   @Override
   public Gender getGender() {
      return Gender.Unknown;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   public String getCompany() {
      return company;
   }

   public void setCompany(String company) {
      this.company = company;
   }

   public String getLocation() {
      return location;
   }

   public void setLocation(String location) {
      this.location = location;
   }

   @Override
   public String toString() {
      return "GithubUser{" +
         "name='" + name + '\'' +
         ", id=" + id +
         ", bio='" + bio + '\'' +
         ", login='" + login + '\'' +
         ", email='" + email + '\'' +
         ", company='" + company + '\'' +
         ", location='" + location + '\'' +
         '}';
   }
}
