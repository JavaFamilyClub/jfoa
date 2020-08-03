package club.javafamily.runner.service;

import club.javafamily.runner.common.model.amqp.RegisterUserInfo;
import club.javafamily.runner.common.service.RedisClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;

@SpringBootTest
public class RedisClientTests {

   @Autowired
   private RedisClient<RegisterUserInfo> redisClient;

   @Test
   public void testInsert() {
      String key = "user";
      RegisterUserInfo info = new RegisterUserInfo();
      info.setAccount("xxx@163.com");
      info.setPassword("asasfsdf-sdfshdfbsdgv-sdfsdv/asdsdf");
      info.setUserName("JavaFamily");

      redisClient.set(key, info, 20);
   }

   @Test
   public void testGet() throws InterruptedException {
      String key = "user";

      RegisterUserInfo info = redisClient.get(key);

      System.out.println("info: " + info);

      TimeUnit.MILLISECONDS.sleep(21);

      RegisterUserInfo info2 = redisClient.get(key);

      System.out.println("info2: " + info2);
   }

}
