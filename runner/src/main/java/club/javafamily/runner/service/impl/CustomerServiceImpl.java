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

import club.javafamily.runner.common.MessageException;
import club.javafamily.runner.common.service.AmqpService;
import club.javafamily.runner.dao.CustomerDao;
import club.javafamily.runner.domain.Customer;
import club.javafamily.runner.service.CustomerService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;

import static club.javafamily.runner.util.SecurityUtil.API_VERSION;

@Service("customerService")
public class CustomerServiceImpl implements CustomerService {

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
   public Integer insertCustomer(Customer user) {
      if(isValid(user)) {
         return customerDao.insert(user);
      }

      return null;
   }

   private boolean isValid(Customer user) {
      if(user == null || StringUtils.isEmpty(user.getAccount())) {
         throw new MessageException("Account must is not empty!");
      }

      if(getCustomerByAccount(user.getAccount()) != null) {
         throw new MessageException("The account(" + user.getAccount() + ") has been registered");
      }

      if(StringUtils.isEmpty(user.getName())) {
         user.autoGeneratorName();
      }

      if(StringUtils.isEmpty(user.getPassword())) {
         user.autoGeneratorPwd();
      }

      return true;
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

   @Async
   @Transactional(readOnly = true)
   @Override
   public void notifySignUpSuccess(Integer id) {
      Customer customer = getCustomer(id);

      if(customer == null) {
         throw new MessageException("Register user is not exist: " + id);
      }

      Map<String, Object> params = new HashMap<>();

      params.put(AMQP_REGISTER_NOTIFY_CUSTOMER_KEY, customer);
      params.put(AMQP_REGISTER_NOTIFY_VERIFY_LINK_KEY,
         // TODO fix link
         "http://localhost:8080" + API_VERSION + "/customer/verify");

      amqpService.sendRegisterMsg(params);
   }

   @Autowired
   public CustomerServiceImpl(AmqpService amqpService, CustomerDao customerDao) {
      this.amqpService = amqpService;
      this.customerDao = customerDao;
   }

   private final AmqpService amqpService;
   private final CustomerDao customerDao;

   public static final String AMQP_REGISTER_NOTIFY_CUSTOMER_KEY = "customer";
   public static final String AMQP_REGISTER_NOTIFY_VERIFY_LINK_KEY = "verifyLink";
   public static final String AMQP_REGISTER_EMAIL_SUBJECT_KEY = "Registered successfully";
}
