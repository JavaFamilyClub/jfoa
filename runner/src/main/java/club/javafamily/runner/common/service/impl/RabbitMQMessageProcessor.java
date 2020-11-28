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

package club.javafamily.runner.common.service.impl;

import club.javafamily.runner.common.model.amqp.*;
import club.javafamily.runner.common.service.AmqpMessageProcessor;
import club.javafamily.runner.common.service.EmailService;
import club.javafamily.runner.util.HTMLTemplateUtils;
import club.javafamily.runner.util.Tool;
import club.javafamily.runner.web.portal.service.SubjectVoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static club.javafamily.runner.common.service.AmqpService.*;
import static club.javafamily.runner.service.impl.CustomerServiceImpl.AMQP_REGISTER_EMAIL_SUBJECT_KEY;

@Service
public class RabbitMQMessageProcessor implements AmqpMessageProcessor {

   @Override
   @RabbitListener(queues = {SEND_TEMPLATE_EMAIL_QUEUE})
   public void receiveSendTemplateEmailMessage(
      @Payload TemplateEmailMessage message)
   {
      try {
         String template = message.getTemplate();
         String subject = message.getSubject();
         String content = HTMLTemplateUtils.render(template, message.getParams());
         emailService.sendMimeMessage(message.getEmail(), subject, content);
      }
      catch(Exception e) {
         LOGGER.warn("RabbitMQ({}) execute error!", SEND_TEMPLATE_EMAIL_QUEUE, e);
      }
   }

   @Override
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

   @Override
   @RabbitListener(queues = { SUBJECT_REQUEST_VOTE_QUEUE })
   public void receiveSubjectRequestChangeVoteMessage(@Payload SubjectRequestChangeVoteMessage message) {
      voteService.processChangeVote(message);
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
   public RabbitMQMessageProcessor(EmailService emailService,
                                   SubjectVoteService voteService)
   {
      this.emailService = emailService;
      this.voteService = voteService;
   }

   private final EmailService emailService;
   private final SubjectVoteService voteService;

   private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQMessageProcessor.class);
}
