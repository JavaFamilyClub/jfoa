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

import club.javafamily.runner.annotation.Audit;
import club.javafamily.runner.annotation.AuditObject;
import club.javafamily.runner.common.MessageException;
import club.javafamily.runner.common.model.amqp.RegisterUserInfo;
import club.javafamily.runner.common.service.AmqpService;
import club.javafamily.runner.common.service.RedisClient;
import club.javafamily.runner.dao.CustomerDao;
import club.javafamily.runner.domain.Customer;
import club.javafamily.runner.enums.ActionType;
import club.javafamily.runner.enums.ResourceEnum;
import club.javafamily.runner.service.CustomerService;
import club.javafamily.runner.util.SecurityUtil;
import club.javafamily.runner.vo.CustomerVO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

import static club.javafamily.runner.util.SecurityUtil.REGISTERED_USER_STORE_PREFIX;

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

   @Transactional(readOnly = true, propagation = Propagation.NESTED)
   @Override
   public Customer getCurrentCustomer() {
      Subject subject = SecurityUtils.getSubject();
      Object principal = subject.getPrincipal();
      Customer user = null;

      if(principal != null) {
         user = getCustomerByAccount(principal.toString());
      }

      return user;
   }

   @Transactional(readOnly = true)
   @Override
   public List<Customer> getCustomers() {
      return customerDao.getAll();
   }

   @Audit(ResourceEnum.Customer)
   @Transactional
   @Override
   public Integer insertCustomer(@AuditObject("getName()") Customer user) {
      if(isValid(user)) {
         user.setPassword(
            SecurityUtil.generatorPassword(user.getAccount(), user.getPassword()));

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

   @Audit(value = ResourceEnum.Customer,
      actionType = ActionType.MODIFY)
   @Transactional
   @Override
   public void updateCustomer(@AuditObject("getName()") Customer user) {
      customerDao.update(user);
   }

   @Audit(value = ResourceEnum.Customer,
      actionType = ActionType.DELETE)
   @Transactional
   @Override
   public void deleteCustomer(@AuditObject("getName()") Customer user) {
      customerDao.delete(user);
   }

   @Async
   @Override
   public void signup(CustomerVO customerVO, String baseLink) {
      RegisterUserInfo info = new RegisterUserInfo();

      info.setAccount(customerVO.getAccount());
      info.setEmail(customerVO.getEmail());
      info.setPassword(SecurityUtil.generatorRegisterUserPassword());
      info.setToken(SecurityUtil.generatorRegisterSuccessToken());
      info.setVerifyBaseLink(baseLink);

      redisClient.set(REGISTERED_USER_STORE_PREFIX + info.getAccount(),
         info, SecurityUtil.DEFAULT_USER_ACTIVE_TIME);

      // sign up success
      amqpService.sendRegisterMsg(info);
   }

   @Autowired
   public CustomerServiceImpl(AmqpService amqpService, CustomerDao customerDao,
                              RedisClient<RegisterUserInfo> redisClient)
   {
      this.amqpService = amqpService;
      this.customerDao = customerDao;
      this.redisClient = redisClient;
   }

   private final RedisClient<RegisterUserInfo> redisClient;
   private final AmqpService amqpService;
   private final CustomerDao customerDao;

   public static final String AMQP_REGISTER_EMAIL_SUBJECT_KEY = "Registered successfully";
}
