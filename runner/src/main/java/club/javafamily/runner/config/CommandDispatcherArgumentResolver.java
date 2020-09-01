package club.javafamily.runner.config;

import club.javafamily.runner.common.socket.CommandDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.invocation.HandlerMethodArgumentResolver;
import org.springframework.messaging.simp.SimpMessagingTemplate;

public class CommandDispatcherArgumentResolver implements HandlerMethodArgumentResolver {
   @Override
   public boolean supportsParameter(MethodParameter parameter) {
      return CommandDispatcher.class.isAssignableFrom(parameter.getParameterType());
   }

   @Override
   public Object resolveArgument(MethodParameter parameter, Message<?> message) {
      return new CommandDispatcher(messagingTemplate);
   }

   @Autowired
   public void setMessagingTemplate(SimpMessagingTemplate messagingTemplate) {
      this.messagingTemplate = messagingTemplate;
   }

   private SimpMessagingTemplate messagingTemplate;
}
