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

import club.javafamily.runner.common.model.amqp.RegisterUserInfo;
import club.javafamily.runner.common.service.*;
import club.javafamily.runner.util.HTMLTemplateUtils;
import club.javafamily.runner.util.Tool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static club.javafamily.runner.service.impl.CustomerServiceImpl.AMQP_REGISTER_EMAIL_SUBJECT_KEY;

@Service("amqpService")
public class AmqpServiceImpl implements AmqpService {

   @Override
   public <T> void publishMsg(String exchange, String routerKey, T params) {
      rabbitTemplate.convertAndSend(exchange, routerKey, params);
   }

   @RabbitListener(queues = { REGISTER_QUEUE })
   public void receiveUserRegisterMessage(@Payload RegisterUserInfo info) {
      try {
         String to = info.getAccount();
         String content = buildRegisterSuccessMailContent(info);
         emailService.sendMimeMessage(to, AMQP_REGISTER_EMAIL_SUBJECT_KEY, content);
      }
      catch(Exception e) {
         LOGGER.warn("{} execute error!", REGISTER_QUEUE, e);
      }
   }

   private String buildRegisterSuccessMailContent(RegisterUserInfo info) {
      String token = info.getToken();
      String link = info.getVerifyBaseLink()
         + Tool.getConcat(info.getVerifyBaseLink())
         + "token=" + token + "&identity=" + info.getAccount();

      Map<String, Object> vars = new HashMap<>();

      vars.put("activeLink", link);
      vars.put("account", info.getAccount());
      vars.put("password", info.getPassword());

      return HTMLTemplateUtils.render(REGISTERED_INFO_MAIL_TEMPLATE, vars);
   }

   @Autowired
   public AmqpServiceImpl(RabbitTemplate rabbitTemplate, EmailService emailService,
                          RedisClient<String> redisClient)
   {
      this.redisClient = redisClient;
      this.emailService = emailService;
      this.rabbitTemplate = rabbitTemplate;
   }

   private final RedisClient<String> redisClient;
   private final EmailService emailService;
   private final RabbitTemplate rabbitTemplate;

   private static final String REGISTERED_INFO_MAIL_TEMPLATE = "signupEmail";

   private static final Logger LOGGER = LoggerFactory.getLogger(AmqpServiceImpl.class);
}
