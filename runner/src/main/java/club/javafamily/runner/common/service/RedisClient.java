package club.javafamily.runner.common.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Collection;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class RedisClient <T extends Serializable> {

   @Resource
   private RedisTemplate<String, T> redisTemplate;

   public void set(String key, T value) {
      set(key, value, INVALID_EXPIRE_TIME);
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

   public Long incr(String key) {
      return incr(key, INVALID_EXPIRE_TIME);
   }

   /**
    * Increment for key, the value must is Number, if not exist, default setting it to 0.
    * @param key key
    * @param seconds expire seconds
    * @return the value after incremented.
    */
   public Long incr(String key, long seconds) {
      Long newValue = getValueOperations().increment(key);

      if(seconds > 0) {
         redisTemplate.expire(key, seconds, TimeUnit.SECONDS);
      }

      return newValue;
   }

   public Long decr(String key) {
      return decr(key, INVALID_EXPIRE_TIME);
   }

   /**
    * Decrement for key, the value must is Number, if not exist, default setting it to 0.
    * @param key key
    * @param seconds expire seconds
    * @return the value after decremented.
    */
   public Long decr(String key, long seconds) {
      Long newValue = getValueOperations().decrement(key);

      if(seconds > 0) {
         redisTemplate.expire(key, seconds, TimeUnit.SECONDS);
      }

      return newValue;
   }

   /**
    * Getting keys that prefix with <code>prefix</code>
    */
   public Set<String> prefixKeys(String prefix) {
      return keys(prefix + "*");
   }

   /**
    * Getting keys.
    * @param pattern Regular expression
    */
   public Set<String> keys(String pattern) {
      return redisTemplate.keys(pattern);
   }

   /**
    * Delete key form redis
    * @return <tt>true</tt> if delete success.
    */
   public Boolean delete(String key) {
      return redisTemplate.delete(key);
   }

   /**
    * Delete all keys form redis
    * @return deleted count.
    */
   public Long delete(Collection<String> keys) {
      return redisTemplate.delete(keys);
   }

   private ValueOperations<String, T> getValueOperations() {
      return redisTemplate.opsForValue();
   }

   private static final int INVALID_EXPIRE_TIME = -1;
}
