package club.javafamily.runner.util;

import club.javafamily.runner.enums.ChartType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.Security;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.TimeZone;

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

   private static final Logger LOGGER = LoggerFactory.getLogger(TestToolStatic.class);
}
