package com.tinyv.sc.driver.service;


import com.tiny.sc.core.domain.Order;

/**
 * @author mayue
 */
public interface DriverService {

    boolean arrangeOrder(Order order);

    void scheduledGrabOrder();

}
