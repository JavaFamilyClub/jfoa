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

package club.javafamily.runner.common.service;

import club.javafamily.runner.common.model.amqp.*;

public interface AmqpService {
   String DIRECT_EXCHANGE = "jfoa-direct";

   String REGISTER_QUEUE = "jfoa-user-register-queue";
   String DIRECT_REGISTER_ROUTER_KEY = DIRECT_EXCHANGE; // routingKey is exchange name

   // change password
   String SEND_TEMPLATE_EMAIL_QUEUE = "jfoa-send-template-email";
   String SEND_TEMPLATE_EMAIL_QUEUE_ROUTER_KEY = SEND_TEMPLATE_EMAIL_QUEUE;

   // subject request
   String SUBJECT_REQUEST_VOTE_QUEUE = "jfoa-subject-request-vote";
   String SUBJECT_REQUEST_VOTE_QUEUE_ROUTER_KEY = SUBJECT_REQUEST_VOTE_QUEUE;

   /**
    * send registered message.
    * @param info user infos.
    */
   default void sendRegisterMsg(RegisterUserInfo info) {
      publishMsg(DIRECT_EXCHANGE, DIRECT_REGISTER_ROUTER_KEY, info);
   }

   /**
    * Send change password message.
    * @param message change password message
    */
   default void sendTemplateEmailMessage(TemplateEmailMessage message) {
      publishMsg(DIRECT_EXCHANGE, SEND_TEMPLATE_EMAIL_QUEUE_ROUTER_KEY, message);
   }

   default void sendSubjectRequestChangeVoteMessage(SubjectRequestChangeVoteMessage message) {
      publishMsg(DIRECT_EXCHANGE, SUBJECT_REQUEST_VOTE_QUEUE_ROUTER_KEY, message);
   }

   /**
    * send message.
    * @param exchange exchange
    * @param routerKey routerKey
    * @param params params
    */
   <T> void publishMsg(String exchange, String routerKey, T params);
}
