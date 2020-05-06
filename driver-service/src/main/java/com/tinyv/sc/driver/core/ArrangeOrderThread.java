package com.tinyv.sc.driver.core;

import com.tiny.sc.core.domain.Constants;
import com.tiny.sc.core.domain.Driver;
import com.tiny.sc.core.domain.Order;
import java.util.concurrent.Callable;

/**
 * @author mayue
 */
public class ArrangeOrderThread implements Callable<Order> {

    private Order order;

    public ArrangeOrderThread(Order order){
        this.order = order;
    }

    @Override
    public Order call() throws Exception {
        int i= 0;
        while(i < 300){
            i++;
            if(OrderLogic.driversQueue.size()>0){
                Driver driver = OrderLogic.driversQueue.take();
                if(driver.getStatus().equals(Constants.DRIVER_STATUS.FREE.name())) {
                    if(OrderLogic.suitable(driver, order)){
                        driver.setStatus(Constants.DRIVER_STATUS.BUSY.name());
                        driver.setGrabOrder(order);
                        order.setDriverId(driver.getDriverId());
                        order.setStatus(Constants.ORDER_STATUS.ACCEPTED.name());
                        return this.order;
                    }else{
                        OrderLogic.driversQueue.put(driver);
                    }
                }else{
                    OrderLogic.driversQueue.put(driver);
                }
            }
        }
        return null;
    }

}
