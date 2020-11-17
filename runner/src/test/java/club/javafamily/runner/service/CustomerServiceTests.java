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

package club.javafamily.runner.service;

import club.javafamily.runner.domain.Customer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CustomerServiceTests {

   @Autowired
   private CustomerService customerService;

   @Test
   public void testInsertUser() {
      Customer customer = new Customer();
      customer.setAccount(new Date().getTime() + "");
      customer.setName("aaa");
      customer.setPassword("aaaa");

      Integer id = customerService.insertCustomer(customer);

      Assertions.assertNotNull(id, "Insert customer error.");

      Customer cust = customerService.getCustomer(id);

      Assertions.assertNotNull(cust, "Getting inserted user is null");
      Assertions.assertEquals(
         cust.getName(), customer.getName(), "Getting insert user is invalid.");
   }
}
