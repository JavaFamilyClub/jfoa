package club.javafamily.runner.util;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.Security;
import java.util.Set;

public class TestToolStatic {

   @Test
   public void algorithms() {
      Set<String> messageDigest = Security.getAlgorithms("Cipher");

      for(String digest : messageDigest) {
         LOGGER.info("MessageDigest: {}", digest);
         System.out.println("MessageDigest: " + digest);
      }
   }

   private static final Logger LOGGER = LoggerFactory.getLogger(TestToolStatic.class);
}
