package com.hhn.vs.centerapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.GenericToStringSerializer;

@Configuration
@EnableRedisRepositories
public class RedisConfig {	 
	
	  @Bean public JedisConnectionFactory redisConnectionFactory() {
		  RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration("localhost", 6379);
		  System.out.println(redisStandaloneConfiguration.getHostName() + " : " + redisStandaloneConfiguration.getPort());
		  return new JedisConnectionFactory(redisStandaloneConfiguration);
	  }
	  
	  @Bean public RedisTemplate<String, Object> redisTemplate() { 
		  final RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
		  template.setConnectionFactory(redisConnectionFactory());
		  template.setValueSerializer(new GenericToStringSerializer<Object>(Object.class)); 
		  return template; 
	  }
}
