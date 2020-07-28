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

package club.javafamily.runner.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class RabbitMQConfig {

   private final AmqpAdmin amqpAdmin;

   public static final String DIRECT_EXCHANGE = "jfoa-direct";

   public static final String REGISTER_QUEUE = "jsof-register-queue";

   @Autowired
   public RabbitMQConfig(AmqpAdmin amqpAdmin) {
      this.amqpAdmin = amqpAdmin;
   }

   @PostConstruct
   private void init() {
      amqpAdmin.deleteExchange(DIRECT_EXCHANGE);
      amqpAdmin.declareExchange(new DirectExchange(DIRECT_EXCHANGE, true, false));

      amqpAdmin.deleteQueue(REGISTER_QUEUE);
      amqpAdmin.declareQueue(new Queue(REGISTER_QUEUE));

      Binding binding = new Binding(REGISTER_QUEUE,
         Binding.DestinationType.QUEUE,
         DIRECT_EXCHANGE,
         DIRECT_EXCHANGE, // routingKey is exchange name
         null);

      amqpAdmin.declareBinding(binding);
   }

}
