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

import club.javafamily.runner.common.model.amqp.TemplateEmailMessage;
import club.javafamily.runner.common.service.AmqpService;
import club.javafamily.runner.common.service.NotifyService;
import club.javafamily.runner.domain.Customer;
import club.javafamily.runner.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Service("notifyService")
public class EmailNotifyService implements NotifyService {

   @Async
   @Override
   public void changePasswordNotify(Customer user, HttpServletRequest request) {
      Map<String, Object> params = new HashMap<>();

      params.put("name", user.getName());
      params.put("account", user.getAccount());
      params.put("url", SecurityUtil.getBaseUrl(request));

      TemplateEmailMessage message
         = new TemplateEmailMessage(CHANGE_PASSWORD_EMAIL_TEMPLATE,
            user.getEmail(), CHANGE_PASSWORD_EMAIL_SUBJECT, params);

      amqpService.sendTemplateEmailMessage(message);
   }

   @Override
   public boolean accept(Customer user) {
      return user != null && StringUtils.hasText(user.getEmail());
   }

   @Autowired
   public EmailNotifyService(AmqpService amqpService) {
      this.amqpService = amqpService;
   }

   private final AmqpService amqpService;

   private static final String CHANGE_PASSWORD_EMAIL_TEMPLATE = "changePassword";
   private static final String CHANGE_PASSWORD_EMAIL_SUBJECT = "Password Changed";
}
