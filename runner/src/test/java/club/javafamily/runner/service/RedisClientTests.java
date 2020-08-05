package club.javafamily.runner.service;

import club.javafamily.runner.common.model.amqp.RegisterUserInfo;
import club.javafamily.runner.common.service.RedisClient;
import club.javafamily.runner.util.SecurityUtil;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
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
