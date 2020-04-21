package com.tinyv.sc.driver.global.config.redis;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

@Component
public class RedisMessageListener implements MessageListener {

    @Override
    public void onMessage(Message message, byte[] pattern) {
        // 消息体
        String body = new String(message.getBody());
        // 渠道名称
        String topic = new String(pattern);
        System.out.println("msg body:"+ body);
        System.out.println("msg topic:"+ topic);
    }
}
