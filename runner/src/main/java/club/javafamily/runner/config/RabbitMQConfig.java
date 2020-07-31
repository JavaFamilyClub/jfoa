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
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;

import javax.annotation.PostConstruct;

import static club.javafamily.runner.common.service.AmqpService.*;

@Configuration
@EnableRabbit
public class RabbitMQConfig {

   private final AmqpAdmin amqpAdmin;

   @Autowired
   public RabbitMQConfig(AmqpAdmin amqpAdmin) {
      this.amqpAdmin = amqpAdmin;
   }

   /**
    * init exchange, queue, binding.
    */
   @PostConstruct
   private void init() {
      amqpAdmin.deleteExchange(DIRECT_EXCHANGE);
      amqpAdmin.declareExchange(new DirectExchange(DIRECT_EXCHANGE, true, false));

      amqpAdmin.deleteQueue(REGISTER_QUEUE);
      amqpAdmin.declareQueue(new Queue(REGISTER_QUEUE));

      Binding binding = new Binding(REGISTER_QUEUE,
         Binding.DestinationType.QUEUE,
         DIRECT_EXCHANGE,
         DIRECT_REGISTER_ROUTER_KEY,
         null);

      amqpAdmin.declareBinding(binding);
   }

   @Bean
   public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
      final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
      rabbitTemplate.setMessageConverter(messageConverter());
      return rabbitTemplate;
   }


   /**
    * Serialize Message to JSON.
    */
   @Bean
   public MessageConverter messageConverter() {
      return new Jackson2JsonMessageConverter();
   }

   @Bean
   public MappingJackson2MessageConverter json2MessageConverter() {
      return new MappingJackson2MessageConverter();
   }
}
