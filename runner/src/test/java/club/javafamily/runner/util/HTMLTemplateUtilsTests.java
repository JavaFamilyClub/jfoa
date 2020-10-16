package club.javafamily.runner.util;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HTMLTemplateUtilsTests {

   @Test
   public void testRender() {
      String template = "signupEmail";
      Map<String, Object> vars = new HashMap<>();
      vars.put("activeLink", "http://javafamily.club");
      vars.put("account", "xxx@163.com");
      vars.put("password", UUID.randomUUID().toString());

      String html = HTMLTemplateUtils.render(template, vars);
      System.out.println("Rendered html: " + html);
   }
}
