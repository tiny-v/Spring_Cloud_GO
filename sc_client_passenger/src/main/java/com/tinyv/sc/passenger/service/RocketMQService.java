package com.tinyv.sc.passenger.service;

import com.tinyv.sc.passenger.domain.Order;

/**
 * @author mayue
 * @Date 2020/04/20
 */
public interface RocketMQService {

    /**
     * 将订单加进消息队列
     * @param order
     */
    void produceOrder(Order order);

}
