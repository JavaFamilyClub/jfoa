package club.javafamily.runner.config;

import club.javafamily.runner.enums.UserType;
import org.apache.shiro.authc.UsernamePasswordToken;

public class OAuthUsernamePasswordToken extends UsernamePasswordToken {

   private UserType userType;

   public OAuthUsernamePasswordToken() {
   }

   public OAuthUsernamePasswordToken(String username, String password, UserType userType) {
      super(username, password);
      this.userType = userType;
   }

   public UserType getUserType() {
      return userType;
   }

   public void setUserType(UserType userType) {
      this.userType = userType;
   }
}
