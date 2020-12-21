/*
 * Copyright (c) 2019, JavaFamily Technology Corp, All Rights Reserved.
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

import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class RedisClient <T extends Serializable> {

   @Resource
   private RedisTemplate<String, T> redisTemplate;

   public void set(String key, T value) {
      set(key, value, INVALID_EXPIRE_TIME);
   }

   public void set(String key, T value, long seconds) {
      ValueOperations<String, T> opsForValue = stringOperator();

      if(seconds > 0) {
         opsForValue.set(key, value, seconds, TimeUnit.SECONDS);
      }
      else {
         opsForValue.set(key, value);
      }
   }

   public T get(String key) {
      return stringOperator().get(key);
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
      Long newValue = stringOperator().increment(key);

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
      Long newValue = stringOperator().decrement(key);

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

   /**
    * lpush to list
    */
   public Long leftPush(String key, T value) {
      ListOperations<String, T> op = listOperator();

      return op.leftPush(key, value);
   }

   /**
    * rpush to list
    */
   public Long rightPush(String key, T value) {
      ListOperations<String, T> op = listOperator();

      return op.rightPush(key, value);
   }

   /**
    * get all list's values.
    */
   public List<T> getListAllValues(String key) {
      return lrange(key, 0, -1);
   }

   /**
    * lrange get list's values by range.
    */
   public List<T> lrange(String key, long start, long end) {
      ListOperations<String, T> op = listOperator();

      return op.range(key, start, end);
   }

   /**
    * get list size
    * <code>lLen key</code>
    */
   public Long listSize(String key) {
      ListOperations<String, T> op = listOperator();

      return op.size(key);
   }

   public void pushFixedList(String key, T value, long count) {
      pushFixedList(key, value, count, INVALID_EXPIRE_TIME);
   }

   /**
    * push value and maintain a fixed list.
    * @param key list key
    * @param value will push's value
    * @param count fixed list size.
    */
   public void pushFixedList(String key, T value, long count, long seconds) {
      ListOperations<String, T> op = listOperator();
      Long size = op.rightPush(key, value);

      if(size != null && size > count) {
         op.trim(key, size - count, size);
      }

      if(seconds > 0) {
         redisTemplate.expire(key, seconds, TimeUnit.SECONDS);
      }
   }

   /**
    * Getting all fixedList's values.
    * @devNote Effects are equivalent to {@link #getListAllValues} now,
    * but this will no longer be equivalent
    * if the fixed List is not <code>trim</code> every after <code>rpush</code>.
    */
   public List<T> getFixedListAllValues(String key, long count) {
      Long size = listSize(key);

      if(size == null || size < count) {
         return lrange(key, 0, -1);
      }

      return lrange(key, size - count, size);
   }

   private ValueOperations<String, T> stringOperator() {
      return redisTemplate.opsForValue();
   }

   private ListOperations<String, T> listOperator() {
      return redisTemplate.opsForList();
   }

   private static final int INVALID_EXPIRE_TIME = -1;
}
