package com.tinyv.sc.driver.core;

import com.tiny.sc.core.domain.Order;
import java.util.concurrent.Callable;

/**
 * @author mayue
 */
public class ProcessOrderThread implements Callable<Boolean> {

    private Order order;

    public ProcessOrderThread(Order order){
        this.order = order;
    }

    @Override
    public Boolean call() throws Exception {
       return OrderLogic.processOrder(this.order);
    }
}
