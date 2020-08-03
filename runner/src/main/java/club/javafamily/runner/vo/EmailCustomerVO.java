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

package club.javafamily.runner.vo;

import club.javafamily.runner.domain.Customer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.validation.constraints.Email;
import java.io.Serializable;
import java.util.Random;
import java.util.UUID;

@JsonIgnoreProperties
public class EmailCustomerVO implements CustomerVO {
   @Email(message = "Email Format Error.")
   private String email;

   public String getEmail() {
      return email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   @Override
   public String toString() {
      return "CustomerVO{" +
         "email='" + email + '\'' +
         '}';
   }

   public Customer convert() {
      Customer user = new Customer();
      user.setAccount(email);
      user.autoGenerator();

      return user;
   }

   @Override
   public String getIdentity() {
      return getEmail();
   }
}
