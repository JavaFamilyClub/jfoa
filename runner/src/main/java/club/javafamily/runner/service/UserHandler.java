package club.javafamily.runner.service;

import club.javafamily.runner.domain.Customer;
import club.javafamily.runner.util.SecurityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserHandler {

   public String getAuditUser() {
      try {
         Customer principal = userService.getCurrentCustomer();

         return principal.getName() + "(" + principal.getAccount() + ")";
      } catch (Exception e) {
         LOGGER.debug("Get principal error!", e);
      }

      return SecurityUtil.Anonymous;
   }

   public static String getDisplayLabel(Customer user) {
      return user.getName() + "(" + user.getAccount() + ")";
   }

   @Autowired
   public UserHandler(CustomerService userService) {
      this.userService = userService;
   }

   private final CustomerService userService;

   private static final Logger LOGGER = LoggerFactory.getLogger(UserHandler.class);
}
