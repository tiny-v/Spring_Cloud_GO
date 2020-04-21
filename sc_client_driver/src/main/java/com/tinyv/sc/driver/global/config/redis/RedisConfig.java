package com.tinyv.sc.driver.global.config.redis;

import com.tinyv.sc.driver.global.config.properties.RedisPoolProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.Topic;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author TinyV
 * @date 2020/4/14
 */
@EnableConfigurationProperties(RedisPoolProperties.class)
@Configuration
public class RedisConfig {

    @Autowired
    private RedisPoolProperties redisPoolProperties;

    /** redis 消息监听器 */
    @Autowired
    private MessageListener redisMessageListener;

    /** 任务池 */
    private ThreadPoolTaskScheduler taskScheduler = null;

    /** 连接工厂 */
    private RedisConnectionFactory connectionFactory = null;

    @Bean(name = "RedisConnectionFactory")
    public RedisConnectionFactory initRedisConnectionFactory(){
        if(this.connectionFactory != null){
            return connectionFactory;
        }
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        // 设置最肖空闲数
        poolConfig.setMinIdle(redisPoolProperties.getMinIdle());
        // 设置最大空闲数
        poolConfig.setMaxIdle(redisPoolProperties.getMaxIdle());
        // 设置最大等待毫秒数
        poolConfig.setMaxWaitMillis(redisPoolProperties.getMaxWait());
        // 设置最大连接数
        poolConfig.setMaxTotal(redisPoolProperties.getMaxActive());
        // 创建 Jedis 连接工厂
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(poolConfig);
        // 获取单机配置
        RedisStandaloneConfiguration rsc = jedisConnectionFactory.getStandaloneConfiguration();
        rsc.setHostName(redisPoolProperties.getHost());
        rsc.setPort(redisPoolProperties.getPort());
        rsc.setPassword(redisPoolProperties.getPassword());
        this.connectionFactory = jedisConnectionFactory;
        return connectionFactory;
    }

    @Bean(name = "redisTemplate")
    public RedisTemplate<Object, Object> initRedisTemplate(){
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();

        RedisSerializer stringRedisSerializer = redisTemplate.getStringSerializer();
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setValueSerializer(stringRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        redisTemplate.setHashValueSerializer(stringRedisSerializer);
        redisTemplate.setConnectionFactory(initRedisConnectionFactory());
        return redisTemplate;
    }

    /**
     * 创建任务池，运行线程等待处理 Redis 的消息
     * @return
     */
    @Bean
    public ThreadPoolTaskScheduler initTaskScheduler(){
        if(taskScheduler != null){
            return taskScheduler;
        }
        taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(20);
        return taskScheduler;
    }

    @Bean
    public RedisMessageListenerContainer initRedisContainer(){
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        // 设置连接工厂
        container.setConnectionFactory(initRedisConnectionFactory());
        // 设置运行任务池
        container.setTaskExecutor(initTaskScheduler());
        // 定义监听渠道， 名称为Order
        Topic topic = new ChannelTopic("Order");
        container.addMessageListener(redisMessageListener, topic);
        return container;
    }



}
