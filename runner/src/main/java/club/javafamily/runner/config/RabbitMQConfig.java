/*
 * Copyright (c) 2019, JavaFamily Technology Corp, All Rights Reserved.
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

package club.javafamily.runner.config;

import club.javafamily.runner.properties.RabbitMQConfigProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;

import javax.annotation.PostConstruct;

import static club.javafamily.runner.common.service.AmqpService.*;

@Configuration
@EnableRabbit
@EnableConfigurationProperties(RabbitMQConfigProperties.class)
public class RabbitMQConfig {

   private final AmqpAdmin amqpAdmin;
   private final RabbitMQConfigProperties properties;

   @Autowired
   public RabbitMQConfig(AmqpAdmin amqpAdmin,
                         RabbitMQConfigProperties properties)
   {
      this.amqpAdmin = amqpAdmin;
      this.properties = properties;
   }

   /**
    * init exchange, queue, binding.
    */
   @PostConstruct
   private void init() {
      if(properties != null && properties.getOverride())  {
         LOGGER.info("############# Override Rabbit MQ Settings #############");

         amqpAdmin.deleteExchange(DIRECT_EXCHANGE);
         amqpAdmin.declareExchange(
            new DirectExchange(DIRECT_EXCHANGE, true, false));

         bindingQueue(DIRECT_EXCHANGE, REGISTER_QUEUE,
            DIRECT_REGISTER_ROUTER_KEY);
         bindingQueue(DIRECT_EXCHANGE, SEND_TEMPLATE_EMAIL_QUEUE,
            SEND_TEMPLATE_EMAIL_QUEUE_ROUTER_KEY);
         bindingQueue(DIRECT_EXCHANGE, SUBJECT_REQUEST_VOTE_QUEUE,
            SUBJECT_REQUEST_VOTE_QUEUE_ROUTER_KEY);
      }
   }

   private void bindingQueue(final String exchange,
                             final String queue,
                             final String routerKey)
   {
      amqpAdmin.deleteQueue(queue);
      amqpAdmin.declareQueue(new Queue(queue));

      Binding binding = new Binding(queue,
         Binding.DestinationType.QUEUE,
         exchange,
         routerKey,
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

   private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQConfig.class);
}
