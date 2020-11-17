/*
 * Copyright (c) 2020, JavaFamily Technology Corp, All Rights Reserved.
 *
 * The software and information contained herein are copyrighted and
 * proprietary to JavaFamily Technology Corp. This software is furnished
 * pursuant to a written license agreement and may be used, copied,
 * transmitted, and stored only in accordance with the terms of such
 * license and with the inclusion of the above copyright notice. Please
 * refer to the file "COPYRIGHT" for further copyright and licensing
 * information. This software and information or any other copies
 * thereof may not be provided or otherwise made available to any other
 * person.
 */

package club.javafamily.runner.common.service;

import club.javafamily.runner.common.model.amqp.RegisterUserInfo;
import club.javafamily.runner.util.SecurityUtil;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.concurrent.TimeUnit;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Disabled("No assert")
public class RedisClientTests {

   @Autowired
   private RedisClient<RegisterUserInfo> redisClient;

   @ParameterizedTest
   @ValueSource(strings = { "test-key" })
   @Order(1)
   public void testInsert(String key) {
      RegisterUserInfo info = new RegisterUserInfo();
      info.setAccount("xxx@163.com");
      info.setPassword(SecurityUtil.generatorRegisterUserPassword());
      info.setToken(SecurityUtil.generatorRegisterSuccessToken());

      redisClient.set(key, info, 20);

      RegisterUserInfo registerUserInfo = redisClient.get(key);

      Assertions.assertEquals(info, registerUserInfo, "Get and Set Value is diff.");
   }

   @ParameterizedTest
   @ValueSource(strings = { "test-key" })
   @Order(2)
   public void testGet(String key) throws InterruptedException {

      RegisterUserInfo info = redisClient.get(key);

      System.out.println("info: " + info);

      TimeUnit.SECONDS.sleep(21);

      RegisterUserInfo info2 = redisClient.get(key);

      System.out.println("info2: " + info2);
   }

}
