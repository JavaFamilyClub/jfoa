package club.javafamily.runner.common.service;

import club.javafamily.runner.domain.Customer;
import com.sun.istack.internal.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.concurrent.TimeUnit;

@Service
public class RedisClient <T extends Serializable> {

   @Resource
   private RedisTemplate<String, T> redisTemplate;

   public void set(String key, T value) {
      set(key, value, -1);
   }

   public void set(String key, T value, long seconds) {
      ValueOperations<String, T> opsForValue = getValueOperations();

      if(seconds > 0) {
         opsForValue.set(key, value, seconds, TimeUnit.SECONDS);
      }
      else {
         opsForValue.set(key, value);
      }
   }

   public T get(String key) {
      return getValueOperations().get(key);
   }

   /**
    * Delete key form redis
    * @return <tt>true</tt> if delete success.
    */
   public Boolean delete(@NotNull String key) {
      return redisTemplate.delete(key);
   }

   private ValueOperations<String, T> getValueOperations() {
      return redisTemplate.opsForValue();
   }
}
