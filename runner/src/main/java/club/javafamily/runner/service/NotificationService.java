/*
 * Copyright (c) 2019, InetSoft Technology Corp, All Rights Reserved.
 *
 * The software and information contained herein are copyrighted and
 * proprietary to InetSoft Technology Corp. This software is furnished
 * pursuant to a written license agreement and may be used, copied,
 * transmitted, and stored only in accordance with the terms of such
 * license and with the inclusion of the above copyright notice. Please
 * refer to the file "COPYRIGHT" for further copyright and licensing
 * information. This software and information or any other copies
 * thereof may not be provided or otherwise made available to any other
 * person.
 */
package club.javafamily.runner.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import static club.javafamily.runner.config.WebSocketConfig.NOTIFY_ALL_TOPIC;

@Service
public class NotificationService {

   @Autowired
   public NotificationService(SimpMessagingTemplate messagingTemplate) {
      this.messagingTemplate = messagingTemplate;
   }

   public void sendNotification(String message) {
      messagingTemplate.convertAndSend(NOTIFY_ALL_TOPIC, message);
   }

   private final SimpMessagingTemplate messagingTemplate;
}
