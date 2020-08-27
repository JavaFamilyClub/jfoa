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

package club.javafamily.runner.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

   @Override
   public void configureMessageBroker(MessageBrokerRegistry registry) {
      // Send Message Prefix(Server Send command prefix)
      registry.enableSimpleBroker("/jf-command");
      // Receive Message Prefix(Web send event prefix)
      registry.setApplicationDestinationPrefixes("/jf-event");
   }

   @Override
   public void registerStompEndpoints(StompEndpointRegistry registry) {
      // Register STOMP Endpoint，Using SockJS
      // web using endpoint create SockJS channel
      registry.addEndpoint("/jf-websocket-channel")
         .setAllowedOrigins("*")
         .withSockJS();
   }

}
