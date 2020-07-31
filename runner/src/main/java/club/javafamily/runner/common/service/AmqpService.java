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

import java.util.Map;

public interface AmqpService {
   String DIRECT_EXCHANGE = "jfoa-direct";
   String REGISTER_QUEUE = "jsoa-user-register-queue";
   String DIRECT_REGISTER_ROUTER_KEY = DIRECT_EXCHANGE; // routingKey is exchange name

   /**
    * send registered message.
    * @param params parameters.
    */
   default void sendRegisterMsg(Map<String, Object> params) {
      publishMsg(DIRECT_EXCHANGE, REGISTER_QUEUE, params);
   }

   /**
    * send message.
    * @param exchange exchange
    * @param routerKey routerKey
    * @param params params
    */
   void publishMsg(String exchange, String routerKey, Map<String, Object> params);
}
