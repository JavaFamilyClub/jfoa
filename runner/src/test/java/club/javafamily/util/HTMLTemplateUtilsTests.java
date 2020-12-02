package club.javafamily.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;

@ExtendWith(SpringExtension.class)
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
