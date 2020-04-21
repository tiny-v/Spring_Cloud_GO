package com.tinyv.sc.passenger.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.common.message.MessageQueue;
import com.tinyv.sc.passenger.domain.Order;
import com.tinyv.sc.passenger.global.config.rocketmq.RocketMQProducer;
import com.tinyv.sc.passenger.service.RocketMQService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author mayue
 * @date 2020/04/20
 */
@Service
public class RocketMQServiceImpl implements RocketMQService {

    @Autowired
    RocketMQProducer rocketMQProducer;

    private static final String sendingTag = "Sending";

    private static final String producerGroup = "spring_cloud_go";

    @Override
    public void produceOrder(Order order) {
        try{
            DefaultMQProducer defaultMQProducer = rocketMQProducer.getRocketMQProducer();
            defaultMQProducer.setProducerGroup(producerGroup);
            String orderJson = JSON.toJSONString(order);
            Message message = null;
            SendResult sendResult = null;
            for(int i=0; i<10000; i++){
                message = new Message("Order", sendingTag, "order_info_"+i, orderJson.getBytes());
                sendResult = defaultMQProducer.send(message);
            }
            MessageQueue messageQueue = sendResult.getMessageQueue();
            String topic = messageQueue.getTopic();
            System.out.println("=== topic ===:" + topic);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
