package club.javafamily.runner;

import club.javafamily.runner.util.SecurityUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RunnerApplicationTests {

   @Test
   public void contextLoads() {
      String account = "admin";
      String password = "admin";
      System.out.println(SecurityUtil.generatorPassword(account, password));
   }

}
