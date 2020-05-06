package com.tinyv.sc.driver.global.config.redis;

import com.alibaba.fastjson.JSON;
import com.tiny.sc.core.domain.Order;
import com.tinyv.sc.driver.core.OrderLogic;
import com.tinyv.sc.driver.core.ArrangeOrderThread;
import com.tinyv.sc.driver.core.ProcessOrderThread;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author mayue
 */
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
            if(OrderLogic.orderQueue.size() > 0){
                Order r = OrderLogic.orderQueue.take();
                Future<Order> future = OrderLogic.executor.submit(new ArrangeOrderThread(r));
                OrderLogic.orderMap.put(order.getOrderId(), order);
                while(!future.isDone()){
                    Thread.sleep(50);
                }
                OrderLogic.executor.submit(new ProcessOrderThread(future.get()));
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
