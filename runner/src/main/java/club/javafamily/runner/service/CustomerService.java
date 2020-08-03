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

package club.javafamily.runner.service;

import club.javafamily.runner.domain.Customer;
import club.javafamily.runner.vo.CustomerVO;

import java.util.List;

public interface CustomerService {
   /**
    * get user by id
    *
    * @return
    */
   Customer getCustomer(Integer id);

   /**
    * get user by name
    *
    * @return
    */
   Customer getCustomerByAccount(String name);

   /**
    * get current
    *
    * @return
    */
   Customer getCurrentCustomer();

   /**
    * get all Users
    *
    * @return
    */
   List<Customer> getCustomers();

   /**
    * insert a user
    *
    * @param user
    * @return
    */
   Integer insertCustomer(Customer user);

   /**
    * update user
    *
    * @param user
    */
   void updateCustomer(Customer user);

   /**
    * delete a user
    */
   void deleteCustomer(Customer user);

   void signup(CustomerVO customerVO, String baseLink);
}
