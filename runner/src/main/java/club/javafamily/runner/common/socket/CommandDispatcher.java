package club.javafamily.runner.common.socket;

import org.springframework.messaging.simp.SimpMessagingTemplate;

import static club.javafamily.runner.config.WebSocketConfig.COMMANDS_TOPIC;

public class CommandDispatcher {

   public void sendCommand(ProjectCommand command) {
      // TODO: messagingTemplate.convertAndSendToUser
      messagingTemplate.convertAndSend(COMMANDS_TOPIC, command);
   }

   public CommandDispatcher(SimpMessagingTemplate messagingTemplate) {
      this.messagingTemplate = messagingTemplate;
   }

   private final SimpMessagingTemplate messagingTemplate;
}
