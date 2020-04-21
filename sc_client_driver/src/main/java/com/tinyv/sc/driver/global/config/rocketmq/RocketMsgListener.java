package com.tinyv.sc.driver.global.config.rocketmq;

import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.tinyv.sc.driver.global.config.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 *
 * @author TinyV
 * @date 2019/11/27
 */
@Component
public class RocketMsgListener implements MessageListenerConcurrently{

    private static final Logger logger = LoggerFactory.getLogger(RocketMsgListener.class) ;

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
        if(CollectionUtils.isEmpty(list)){
            logger.info("所有的消息都消费完了");
        }
        MessageExt messageExt = list.get(0);
        logger.info("接受到的消息为： key:"+ messageExt.getKeys() + ", body: "+new String(messageExt.getBody()));
        int reConsume = messageExt.getReconsumeTimes();
        // 消息已经重试了3次，如果不需要再次消费，则返回成功
        if(reConsume == Constants.MQ_MSG_RETRY_TIMES){
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        }
        if(Constants.MQ_TOPIC.equals(messageExt.getTopic())){
            String tags = messageExt.getTags() ;
            switch (tags){
                case "Sending":
                    //logger.info("== 客户发单啦 =="+tags);
                    break;
                case "Cancel":
                    //logger.info("== 客户取消订单啦 =="+tags);
                    break;
                default:
                    //logger.info("未匹配到Tag == >>"+tags);
                    break;
            }
        }
        // 消息消费成功
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }
}
