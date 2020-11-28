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
import club.javafamily.runner.enums.*;
import club.javafamily.runner.service.CustomerService;
import club.javafamily.runner.util.SecurityUtil;
import club.javafamily.runner.vo.CustomerVO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

import static club.javafamily.runner.util.SecurityUtil.REGISTERED_USER_STORE_PREFIX;

@Service("customerService")
@CacheConfig(cacheNames = "customer-cache")
public class CustomerServiceImpl implements CustomerService {

   @Transactional(readOnly = true)
   @Cacheable(key = "'user_' + #p0")
   @Override
   public Customer getCustomer(Integer id) {
      return customerDao.get(id);
   }

   @Transactional(readOnly = true)
   @Cacheable(key = "'user_by_account_' + #p0")
   @Override
   public Customer getCustomerByAccount(String account) {
      return customerDao.getUserByAccount(account);
   }

   @Transactional(readOnly = true)
   @Override
   public String getAuditUser() {
      try {
         Customer principal = getCurrentCustomer();

         return principal.getName() + "(" + principal.getAccount() + ")";
      } catch (Exception ignore) {
         LOGGER.debug("Get principal error!", ignore);
      }

      return SecurityUtil.Anonymous;
   }

   @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
   @Override
   public Customer getCurrentCustomer() {
      Object principal = null;

      try {
         Subject subject = SecurityUtils.getSubject();
         principal = subject.getPrincipal();
      }
      catch(Exception ignore) {
         LOGGER.debug("Subject get principal error.");
      }

      Customer user = null;

      if(principal != null) {
         user = getCustomerByAccount(principal.toString());
      }

      return user;
   }

   @Transactional(readOnly = true)
   @Cacheable(key = "'users'")
   @Override
   public List<Customer> getCustomers() {
      return customerDao.getAll();
   }

   @Audit(ResourceEnum.Customer)
   @Transactional
   @CacheEvict(allEntries = true)
   @Override
   public Integer insertCustomer(@AuditObject("getAccount()") Customer user) {
      if(isValid(user)) {
         // password encryption
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

      // password is not required for github user.
      if(StringUtils.isEmpty(user.getPassword()) && (UserType.User.equals(user.getType()))) {
         throw new MessageException("Password must is not empty!");
      }

      if(getCustomerByAccount(user.getAccount()) != null) {
         throw new MessageException("The account(" + user.getAccount() + ") has been registered");
      }

      if(StringUtils.isEmpty(user.getName())) {
         user.autoGeneratorName();
      }

      return true;
   }

   @Audit(value = ResourceEnum.Customer,
      actionType = ActionType.MODIFY)
   @Transactional
   @Caching(
      put = {
         @CachePut(key = "'user_' + #p0.id"),
         @CachePut(key = "'user_by_account_' + #p0.account")
      },
      evict = {
         @CacheEvict(key = "'users'"),
      }
   )
   @Override
   public Customer updateCustomer(@AuditObject("getName()") Customer user) {
      customerDao.update(user);

      return customerDao.get(user.getId());
   }

   @Audit(value = ResourceEnum.Customer,
      actionType = ActionType.DELETE)
   @CacheEvict(allEntries = true)
   @Transactional
   @Override
   public void deleteCustomer(@AuditObject("getName()") Customer user) {
      customerDao.delete(user);
   }

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

   private static final Logger LOGGER = LoggerFactory.getLogger(CustomerServiceImpl.class);
}
