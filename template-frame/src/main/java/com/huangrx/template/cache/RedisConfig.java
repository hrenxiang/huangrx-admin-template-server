package com.huangrx.template.cache;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

/**
 * Redis 配置类 spring boot 3.x
 * <p>
 * RedisTemplate配置
 * <p>
 * RedisTemplate默认使用的是JdkSerializationRedisSerializer，StringRedisTemplate默认使用的是StringRedisSerializer
 * <p>
 * 但是JdkSerializationRedisSerializer是二进制形式存储，不方便我们查看，所以自定义序列化类
 * <p>
 * 1.使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值
 * <p>
 * 2.使用StringRedisSerializer来序列化和反序列化redis的key值
 * <p>
 * 3.使用RedisTemplate<String, Object>类型来定义redisTemplate，并且设置初始化方法
 * <p>
 * 4.将redisTemplate设置到RedisCacheManager
 *
 * @author hrenxiang
 * @since 2022-04-24 8:26 PM
 */
@Configuration
@EnableCaching
public class RedisConfig implements CachingConfigurer {

    @Bean
    public RedisSerializer<String> keySerializer() {
        return new StringRedisSerializer();
    }

    @Bean
    public RedisSerializer<Object> valueSerializer() {
        //创建JSON序列化器
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.activateDefaultTyping(
                LaissezFaireSubTypeValidator.instance,
                ObjectMapper.DefaultTyping.NON_FINAL,
                JsonTypeInfo.As.WRAPPER_ARRAY);

        return new Jackson2JsonRedisSerializer<>(objectMapper, Object.class);
    }

    /**
     * 在springboot 2.x 中，没有 RedisCacheManager rcm = new RedisCacheManager(redisTemplate); 此构造器
     * <p>
     * 我们将使用
     * RedisCacheManager = RedisCacheManager.create(redisConnectionFactory);
     * 或
     * new RedisCacheManager(redisCacheWriter, redisCacheConfiguration);
     */
    @Bean
    public CacheManager redisCacheManager(RedisConnectionFactory redisConnectionFactory) {
        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory);
        //设置Redis缓存有效期为1天
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                //设置key序列化器
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(keySerializer()))
                //设置value序列化器
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(valueSerializer()))
                //设置缓存的默认超时时间：分钟-ofMinutes  天-ofDays
                .entryTtl(Duration.ofDays(1))
                //如果是空值，不缓存
                .disableCachingNullValues();
        return new RedisCacheManager(redisCacheWriter, redisCacheConfiguration);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        // key 和 value
        // 一般来说， redis-key采用字符串序列化；
        // redis-value采用json序列化， json的体积小，可读性高，不需要实现serializer接口。
        redisTemplate.setKeySerializer(keySerializer());
        redisTemplate.setValueSerializer(valueSerializer());

        redisTemplate.setHashKeySerializer(keySerializer());
        redisTemplate.setHashValueSerializer(valueSerializer());

        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

}
