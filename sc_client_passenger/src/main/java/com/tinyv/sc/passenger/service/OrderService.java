package com.tinyv.sc.passenger.service;

import com.tinyv.sc.passenger.domain.Driver;
import com.tinyv.sc.passenger.domain.Order;
import com.tinyv.sc.passenger.domain.Passenger;

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


    /**
     * 抢单
     * @param orderId
     * @return 是否抢到订单
     */
    boolean receiveOrder(Driver driver, String orderId);


    /**
     * 处理订单
     * @param orderId
     * @return
     */
    boolean processOrder(Driver driver, String orderId);



}
