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

import club.javafamily.runner.util.SecurityUtil;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.*;

@Entity(name = "t_customer")
public class Customer implements Serializable {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer id;
   private String name;
   private String account;
   private String password;
   private boolean verify;
   private Date registerDate;

   @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
   @ManyToMany(targetEntity = Role.class, fetch = FetchType.EAGER)
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

   public Set<Role> getRoles() {
      return roles;
   }

   public void setRoles(Set<Role> roles) {
      this.roles = roles;
   }

   public boolean isVerify() {
      return verify;
   }

   public void setVerify(boolean verify) {
      this.verify = verify;
   }

   public Date getRegisterDate() {
      return registerDate;
   }

   public void setRegisterDate(Date registerDate) {
      this.registerDate = registerDate;
   }

   @Override
   public String toString() {
      return "Customer{" +
         "id=" + id +
         ", name='" + name + '\'' +
         ", account='" + account + '\'' +
         ", registerDate='" + registerDate + '\'' +
         '}';
   }

   public boolean isAdmin() {
      return SecurityUtil.Admin.equals(this.account);
   }

   public void autoGenerator() {
      autoGeneratorName();
      autoGeneratorPwd();
      registerDate = new Date(System.currentTimeMillis());
   }

   public void autoGeneratorName() {
      setName("JavaFamily" + random.nextInt());
   }

   public void autoGeneratorPwd() {
      setPassword(UUID.randomUUID().toString());
   }

   private static final Random random = new Random();
}
