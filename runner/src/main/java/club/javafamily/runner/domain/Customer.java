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

package club.javafamily.runner.domain;

import club.javafamily.runner.enums.Gender;
import club.javafamily.runner.enums.UserType;
import club.javafamily.runner.util.SecurityUtil;
import club.javafamily.runner.util.Tool;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cascade;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import static club.javafamily.runner.util.Tool.DEFAULT_TIME_ZONE;

@Entity(name = "t_customer")
public class Customer implements Serializable, Cloneable {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer id;
   private String account;
   private String name;
   @JsonIgnore
   private String password;
   private String email;
   private Gender gender = Gender.Unknown;
   private UserType type = UserType.User;
   private boolean active;
   @DateTimeFormat(pattern = Tool.DEFAULT_DATETIME_FORMAT)
   @JsonFormat(pattern=Tool.DEFAULT_DATETIME_FORMAT, timezone = DEFAULT_TIME_ZONE)
   private Date registerDate;

   @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
   @ManyToMany(targetEntity = Role.class, fetch = FetchType.LAZY)
   private Set<Role> roles;

   public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getAccount() {
      return account;
   }

   public void setAccount(String account) {
      this.account = account;
   }

   public String getPassword() {
      return password;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public String getEmail() {
      return email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   public Set<Role> getRoles() {
      return roles;
   }

   public void setRoles(Set<Role> roles) {
      this.roles = roles;
   }

   public boolean isActive() {
      return active;
   }

   public void setActive(boolean active) {
      this.active = active;
   }

   public Date getRegisterDate() {
      return registerDate;
   }

   public void setRegisterDate(Date registerDate) {
      this.registerDate = registerDate;
   }

   public Gender getGender() {
      return gender;
   }

   public void setGender(Gender gender) {
      this.gender = gender;
   }

   public UserType getType() {
      return type;
   }

   public void setType(UserType type) {
      if(type == null) {
         return;
      }

      this.type = type;
   }

   @Override
   public String toString() {
      return "Customer{" +
         "id=" + id +
         ", account='" + account + '\'' +
         ", name='" + name + '\'' +
         ", password='" + password + '\'' +
         ", email='" + email + '\'' +
         ", gender=" + gender +
         ", type=" + type +
         ", active=" + active +
         ", registerDate=" + registerDate +
         ", roles=" + roles +
         '}';
   }

   @Override
   public Object clone() throws CloneNotSupportedException {
      Customer customer = (Customer) super.clone();
      customer.id = id;
      customer.account = account;
      customer.name = name;
      customer.password = password;
      customer.active = active;
      customer.type = type;
      customer.registerDate = registerDate;
      customer.roles = Tool.deepCloneCollection(roles);

      return customer;
   }

   @JsonIgnore
   public boolean isAdmin() {
      return SecurityUtil.Admin.equals(this.account);
   }

   public void autoGenerator() {
      autoGeneratorName();
      autoGeneratorPwd();
      registerDate = new Date();
   }

   public void autoGeneratorName() {
      setName(Tool.PROJECT_MAIN + Tool.getSecureRandom().nextInt());
   }

   public void autoGeneratorPwd() {
      setPassword(SecurityUtil.generatorRegisterUserPassword());
   }
}
