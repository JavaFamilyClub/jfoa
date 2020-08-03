package club.javafamily.runner.common.service;

import club.javafamily.runner.domain.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

@Service
public class RedisClient <T extends Serializable> {

   private final RedisTemplate redisTemplate;

   @Autowired
   public RedisClient(RedisTemplate redisTemplate) {
      this.redisTemplate = redisTemplate;
   }

   public void set(String key, T value) {
      set(key, value, -1);
   }

   public void set(String key, T value, long millis) {
      ValueOperations<String, T> opsForValue = getValueOperations();

      if(millis > 0) {
         opsForValue.set(key, value, millis, TimeUnit.MILLISECONDS);
      }
      else {
         opsForValue.set(key, value);
      }
   }

   public T get(String key) {
      return getValueOperations().get(key);
   }

   public boolean delete(String key) {
      return redisTemplate.delete(key);
   }

   private ValueOperations<String, T> getValueOperations() {
      return redisTemplate.opsForValue();
   }
}
