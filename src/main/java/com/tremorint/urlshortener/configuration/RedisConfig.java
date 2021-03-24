package com.tremorint.urlshortener.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tremorint.urlshortener.domain.Url;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    private ObjectMapper objectMapper;
    private RedisConnectionFactory redisConnectionFactory;


    //autowire ObjectMapper and RedisConnectionFactory Objects
    public RedisConfig(ObjectMapper objectMapper, RedisConnectionFactory redisConnectionFactory) {
        this.objectMapper = objectMapper;
        this.redisConnectionFactory = redisConnectionFactory;
    }

    @SuppressWarnings({"rawtypes","unchecked"})
    @Bean
    public RedisTemplate<String,Url> redisTemplate() {
        //jackson json redis serializer init with class type and set object mapper
        final Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Url.class);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);

        //create the template object and set the serializers and connection factory for it
        final RedisTemplate<String,Url> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        return redisTemplate;
    }
}
