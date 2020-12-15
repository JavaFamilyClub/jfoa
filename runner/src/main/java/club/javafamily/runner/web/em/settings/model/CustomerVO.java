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

package club.javafamily.runner.web.em.settings.model;

import club.javafamily.commons.utils.Tool;
import club.javafamily.runner.domain.Customer;
import club.javafamily.commons.enums.Gender;
import club.javafamily.commons.enums.UserType;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

public class CustomerVO implements Serializable {
   private int id;
   private String account;
   private String name;
   private String email;
   private UserType type;
   private Gender gender;
   private boolean active;

   @DateTimeFormat(pattern = Tool.DEFAULT_DATETIME_FORMAT)
   @JsonFormat(pattern=Tool.DEFAULT_DATETIME_FORMAT, timezone = Tool.DEFAULT_TIME_ZONE_STR)
   private Date registerDate;

   public static CustomerVO buildFromDomain(Customer customer) {
      assert customer != null;
      CustomerVO vo = new CustomerVO();

      vo.setId(customer.getId());
      vo.setAccount(customer.getAccount());
      vo.setActive(customer.isActive());
      vo.setEmail(customer.getEmail());
      vo.setName(customer.getName());
      vo.setType(customer.getType());
      vo.setGender(customer.getGender());
      vo.setRegisterDate(customer.getRegisterDate());

      return vo;
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

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getEmail() {
      return email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   public UserType getType() {
      return type;
   }

   public void setType(UserType type) {
      this.type = type;
   }

   public Gender getGender() {
      return gender;
   }

   public void setGender(Gender gender) {
      this.gender = gender;
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

   @Override
   public String toString() {
      return "CustomerVO{" +
         "id=" + id +
         ", account='" + account + '\'' +
         ", name='" + name + '\'' +
         ", email='" + email + '\'' +
         ", type=" + type +
         ", gender=" + gender +
         ", active=" + active +
         ", registerDate=" + registerDate +
         '}';
   }
}
