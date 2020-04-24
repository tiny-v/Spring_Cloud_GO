package com.tinyv.sc.driver.global.config.redis;

import com.alibaba.fastjson.JSON;
import com.tinyv.sc.driver.core.OrderLogic;
import com.tinyv.sc.driver.core.OrderThread;
import com.tinyv.sc.driver.domain.Order;
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
        Order order = JSON.toJavaObject(JSON.parseObject(body), Order.class);
        // 将订单放入Map
        try {
            OrderLogic.total_order_num.incrementAndGet();
            OrderLogic.orderQueue.put(order);
            if(OrderLogic.orderQueue.size()>0){
                Order r = OrderLogic.orderQueue.take();
                OrderLogic.executorService.submit(new OrderThread(r));
                OrderLogic.orderMap.put(order.getOrderId(), order);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
