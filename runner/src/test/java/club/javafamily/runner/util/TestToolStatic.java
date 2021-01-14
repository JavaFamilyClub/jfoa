package club.javafamily.runner.util;

import club.javafamily.commons.enums.ChartType;
import club.javafamily.commons.utils.Tool;
import club.javafamily.runner.enums.ResourceEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.management.remote.*;
import java.io.IOException;
import java.security.Security;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;

public class TestToolStatic {

   @Test
   public void algorithms() {
      Set<String> messageDigest = Security.getAlgorithms("Cipher");

      for(String digest : messageDigest) {
         LOGGER.info("MessageDigest: {}", digest);
         System.out.println("MessageDigest: " + digest);
      }
   }

   @Test
   public void dateTime() {
      System.out.println(Instant.now().toString());

      LocalDateTime dateTime = LocalDateTime.now();

      System.out.println(dateTime);
   }

   @Test
   public void jackson() throws Exception {
      ObjectMapper mapper = new ObjectMapper();

      Assertions.assertEquals("\"bar\"", mapper.writeValueAsString(ChartType.bar));
   }

   @Test
   public void timeZone() {
      TimeZone timeZone = TimeZone.getTimeZone(Tool.DEFAULT_TIME_ZONE_STR);

      Assertions.assertNotNull(timeZone, "Default time zone is null");
   }

   @Test
   public void enumTests() {
      Assertions.assertEquals(ResourceEnum.PAGE_EM.getPermissionFlag(), ResourceEnum.PAGE_EM.name(), "ResourceEnum.getPermissionFlag error.");
   }

   @Test
   public void jmx() {
      String hostName = "101.133.167.196";
      int portNum = 10888;

      try {
         JMXServiceURL u = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://"
            + hostName + ":" + portNum + "/jmxrmi");

         Map<String, Object> auth = new HashMap<>();
         auth.put(JMXConnector.CREDENTIALS, new String[] { "dreamli", "dreamLi0812" });

         JMXConnector c = JMXConnectorFactory.connect(u, auth);
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   private static final Logger LOGGER = LoggerFactory.getLogger(TestToolStatic.class);
}
