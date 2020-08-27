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

package club.javafamily.runner.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.io.Serializable;

@Configuration
public class RedisConfig {

   @Bean("redisTemplate")
   public RedisTemplate<String, Serializable> redisTemplate(
      RedisConnectionFactory redisConnectionFactory)
   {
      RedisTemplate<String, Serializable> template = new RedisTemplate();
      template.setConnectionFactory(redisConnectionFactory);

      Jackson2JsonRedisSerializer jackson2JsonRedisSerializer
         = new Jackson2JsonRedisSerializer(Serializable.class);
      ObjectMapper om = new ObjectMapper();
      om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
      om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
      jackson2JsonRedisSerializer.setObjectMapper(om);

      StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
      // key using StringSerializer
      template.setKeySerializer(stringRedisSerializer);
      // key of hash using StringSerializer
      template.setHashKeySerializer(stringRedisSerializer);

      // value using jackson serializer
      template.setValueSerializer(jackson2JsonRedisSerializer);
      template.setHashValueSerializer(jackson2JsonRedisSerializer);

      template.afterPropertiesSet();

      return template;
   }
}
