package club.javafamily.runner.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.Serializable;

@Configuration
public class RedisConfig {

//   @Bean("redisTemplate")
//   public RedisTemplate<String, Serializable> redisTemplate(
//      RedisConnectionFactory redisConnectionFactory)
//   {
//      RedisTemplate<String, Serializable> template = new RedisTemplate();
//      template.setConnectionFactory(redisConnectionFactory);
//
//      return template;
//   }
}
