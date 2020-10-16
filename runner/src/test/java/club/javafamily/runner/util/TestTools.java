/*
 * Copyright (c) 2019, JavaFamily Technology Corp, All Rights Reserved.
 *
 * The software and information contained herein are copyrighted and
 * proprietary to AngBoot Technology Corp. This software is furnished
 * pursuant to a written license agreement and may be used, copied,
 * transmitted, and stored only in accordance with the terms of such
 * license and with the inclusion of the above copyright notice. Please
 * refer to the file "COPYRIGHT" for further copyright and licensing
 * information. This software and information or any other copies
 * thereof may not be provided or otherwise made available to any other
 * person.
 */

package club.javafamily.runner.util;

import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.Security;
import java.util.Set;

@SpringBootTest
public class TestTools {

   @Autowired
   private StringEncryptor encryption;

   @Test
   public void algorithms() {
      Set<String> messageDigest = Security.getAlgorithms("Cipher");

      for(String digest : messageDigest) {
         LOGGER.info("MessageDigest: {}", digest);
         System.out.println("MessageDigest: " + digest);
      }
   }

   @Test
   public void generatorEnvPassword() {
      String password = "";

      System.out.println(encryption.encrypt(password));
   }

   private static final Logger LOGGER = LoggerFactory.getLogger(TestTools.class);
}
