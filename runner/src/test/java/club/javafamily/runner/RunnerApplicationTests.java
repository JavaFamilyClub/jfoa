package club.javafamily.runner;

import club.javafamily.util.SecurityUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class RunnerApplicationTests {

   @Test
   public void contextLoads() {
      String account = "admin";
      String password = "admin";
      System.out.println(SecurityUtil.generatorPassword(account, password));
   }

}
