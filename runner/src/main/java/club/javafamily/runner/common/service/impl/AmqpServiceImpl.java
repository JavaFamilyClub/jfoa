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

package club.javafamily.runner.common.service.impl;

import club.javafamily.runner.common.service.AmqpService;
import club.javafamily.runner.common.service.EmailService;
import club.javafamily.runner.domain.Customer;
import club.javafamily.runner.util.SecurityUtil;
import club.javafamily.runner.util.Tool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

import static club.javafamily.runner.service.impl.CustomerServiceImpl.*;

@Service("amqpService")
public class AmqpServiceImpl implements AmqpService {

   @Override
   public void publishMsg(String exchange, String routerKey, Map<String, Object> params) {
      rabbitTemplate.convertAndSend(exchange, routerKey, params);
   }

   @RabbitListener(queues = { REGISTER_QUEUE })
   public void receiveUserRegisterMessage(Message message) {
      byte[] body = message.getBody();
      MessageProperties properties = message.getMessageProperties();

      System.out.println("Receive body: " + body);
      System.out.println("Receive properties: " + properties);
   }

   @RabbitListener(queues = { REGISTER_QUEUE })
   public void receiveUserRegisterMessage(Map<String, Object> params) {
      Customer customer = null;

      try {
         customer = (Customer) params.get(AMQP_REGISTER_NOTIFY_CUSTOMER_KEY);
         String link = (String) params.get(AMQP_REGISTER_NOTIFY_VERIFY_LINK_KEY);
         String to = customer.getAccount();
         String content = buildRegisterSuccessMailContent(customer, link);
         emailService.sendMimeMessage(to, AMQP_REGISTER_EMAIL_SUBJECT_KEY, content);
      }
      catch(Exception e) {
         LOGGER.warn("{} execute error!", REGISTER_QUEUE, e);
      }
   }

   private String buildRegisterSuccessMailContent(Customer user, String baseLink) {
      StringBuilder sb = new StringBuilder();
      String name = Objects.toString(user.getName(), user.getAccount());
      sb.append(name);
      sb.append(":<br>");
      sb.append("    Congratulations on your successful registration, please click the link below to activate your account:");
      sb.append("<br>");

      String token = SecurityUtil.generatorRegisterSuccessToken();
      String link = baseLink + Tool.getConcat(baseLink)
         + "token=" + token + "&customerId=" + user.getId();

      sb.append("<a href='");
      sb.append(link);
      sb.append("'>");
      sb.append(link);
      sb.append("</a>");

      return sb.toString();
   }

   @Autowired
   public AmqpServiceImpl(RabbitTemplate rabbitTemplate, EmailService emailService) {
      this.emailService = emailService;
      this.rabbitTemplate = rabbitTemplate;
   }

   private final EmailService emailService;
   private final RabbitTemplate rabbitTemplate;

   private static final Logger LOGGER = LoggerFactory.getLogger(AmqpServiceImpl.class);
}
