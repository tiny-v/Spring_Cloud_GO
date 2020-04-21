package com.tinyv.sc.passenger.global.config.rocketmq;

import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.tinyv.sc.passenger.global.config.properties.RocketMQProducerProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author TinyV
 * @date 2019/11/27
 */
@Configuration
@EnableConfigurationProperties(RocketMQProducerProperty.class)
public class RocketMQProducer {

    @Autowired
    private RocketMQProducerProperty rocketMQProducerProperty;

    @Bean
    public DefaultMQProducer getRocketMQProducer(){
        // 生产者的组名
        DefaultMQProducer producer = new DefaultMQProducer(rocketMQProducerProperty.getGroupName());
        // 指定NameServer地址，多个地址以 ; 隔开
        producer.setNamesrvAddr(rocketMQProducerProperty.getNamesrvAddr());

        if(rocketMQProducerProperty.getMaxMessageSize()!=null){
            producer.setMaxMessageSize(rocketMQProducerProperty.getMaxMessageSize());
        }
        if(rocketMQProducerProperty.getSendMsgTimeout()!=null){
            producer.setSendMsgTimeout(rocketMQProducerProperty.getSendMsgTimeout());
        }
        //如果发送消息失败，设置重试次数，默认为2次
        if(rocketMQProducerProperty.getRetryTimesWhenSendFailed()!=null){
            producer.setRetryTimesWhenSendFailed(rocketMQProducerProperty.getRetryTimesWhenSendFailed());
        }
        try {
            producer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }
        return producer;
    }

}
