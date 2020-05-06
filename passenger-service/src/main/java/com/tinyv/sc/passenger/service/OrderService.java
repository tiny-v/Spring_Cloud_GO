package com.tinyv.sc.passenger.service;


import com.tiny.sc.core.domain.Passenger;

/**
 * 订单接口
 * interface
 * @author mayue
 * @date 2020/04/20
 */
public interface OrderService {

    /**
     * 发送订单
     * @param passenger
     */
    void sendOrder(Passenger passenger);

    /**
     * 取消订单
     * @param orderId
     */
    void cancelOrder(Passenger passenger, String orderId);





}
