package com.tinyv.sc.driver.global.config.rocketmq;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.tinyv.sc.driver.global.config.properties.RocketMQConsumerProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 *
 * @author TinyV
 * @date 2019/11/27
 */
@Configuration
@EnableConfigurationProperties(RocketMQConsumerProperty.class)
public class RocketMQConsumer {

    @Autowired
    private RocketMQConsumerProperty consumerProperty;

    @Resource
    private RocketMsgListener msgListener;

    @Bean
    public DefaultMQPushConsumer getRocketMQConsumer(){
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(consumerProperty.getGroupName());
        consumer.setNamesrvAddr(consumerProperty.getNamesrvAddr());
        consumer.setConsumeThreadMin(consumerProperty.getConsumeThreadMin());
        consumer.setConsumeThreadMax(consumerProperty.getConsumeThreadMax());
        consumer.registerMessageListener(msgListener);
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        consumer.setConsumeMessageBatchMaxSize(consumerProperty.getConsumeMessageBatchMaxSize());
        try {
            String[] topicTagsArr = consumerProperty.getTopics().split(";");
            for (String topicTags : topicTagsArr) {
                String[] topicTag = topicTags.split("~");
                consumer.subscribe(topicTag[0], topicTag[1]);
            }
            consumer.start();
        }catch (MQClientException e){
            e.printStackTrace();
        }
        return consumer;
    }

}
