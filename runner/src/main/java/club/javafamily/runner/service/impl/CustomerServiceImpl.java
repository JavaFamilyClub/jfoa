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

package club.javafamily.runner.service.impl;

import club.javafamily.runner.dao.CustomerDao;
import club.javafamily.runner.domain.Customer;
import club.javafamily.runner.service.CustomerService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("customerService")
public class CustomerServiceImpl implements CustomerService {

   @Autowired
   public CustomerServiceImpl(CustomerDao customerDao) {
      this.customerDao = customerDao;
   }

   @Transactional(readOnly = true)
   @Override
   public Customer getCustomer(Integer id) {
      return customerDao.get(id);
   }

   @Transactional(readOnly = true)
   @Override
   public Customer getCustomerByAccount(String name) {
      return customerDao.getUserByAccount(name);
   }

   @Transactional(readOnly = true)
   @Override
   public Customer getCurrentCustomer() {
      Subject subject = SecurityUtils.getSubject();
      Object principal = subject.getPrincipal();
      Customer user = null;

      if (principal != null) {
         user = getCustomerByAccount(principal.toString());
      }

      return user;
   }

   @Transactional(readOnly = true)
   @Override
   public List<Customer> getCustomers() {
      return customerDao.getAll();
   }

   @Transactional
   @Override
   public void insertCustomer(Customer user) {
      customerDao.insert(user);
   }

   @Transactional
   @Override
   public void updateCustomer(Customer user) {
      customerDao.update(user);
   }

   @Transactional
   @Override
   public void deleteCustomer(Customer user) {
      customerDao.delete(user);
   }

   private final CustomerDao customerDao;
}
