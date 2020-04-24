package com.tinyv.sc.driver.core;

import com.tiny.sc.core.domain.Driver;
import com.tiny.sc.core.domain.Order;

import java.util.concurrent.Callable;

/**
 * @author mayue
 */
public class OrderThread implements Callable<Boolean> {

    private Order order;

    private Driver driver;

    public OrderThread(Order order){
        this.order = order;
    }

    @Override
    public Boolean call() throws Exception {
        int i= 0;
        while(i<300){
            i++;
            if(OrderLogic.driversQueue.size()>0){
                Driver driver = OrderLogic.driversQueue.take();
                if(driver.getStatus().equals("FREE")) {
                    if(OrderLogic.suitable(driver, order)){
                        driver.setStatus("BUSY");
                        driver.setGrabOrder(order);
                        order.setStatus("ACCEPTED");
                        this.driver = driver;
                        break;
                    }else{
                        OrderLogic.driversQueue.put(driver);
                    }
                }else{
                    OrderLogic.driversQueue.put(driver);
                }
            }
        }

        if(this.driver!=null && driver.getGrabOrder()!=null){
            OrderLogic.in_process_order_num.addAndGet(1);
            // todo：调用passenger服务，更新乘客状态为已接单
            // 计算订单耗时
            long consumerTime = OrderLogic.getOrderCostTime(driver.getLocation().getPoint(), driver.getGrabOrder());
            // System.out.println("== 司机 " + driver.getName() + "正在执行订单, 需要耗时:"+ consumerTime);
            // 当前线程沉睡
            Thread.sleep(consumerTime);
            // 订单结束后, 更新司机位置
            //System.out.println("== 司机 " + driver.getName() + "订单执行完成, 位置从 （"+driver.getLocation().getPoint().getPoint_X()+","+driver.getLocation().getPoint().getPoint_Y()+
            //        "), 更新为("+driver.getGrabOrder().getEndPoint().getPoint().getPoint_X()+","+driver.getGrabOrder().getEndPoint().getPoint().getPoint_Y() +")");
            driver.setLocation(driver.getGrabOrder().getEndPoint());
            order.setStatus("FINISH");
            OrderLogic.finished_order_num.incrementAndGet();
            OrderLogic.orderMap.remove(order.getOrderId());
            driver.setStatus("FREE");
            OrderLogic.driversQueue.put(driver);
            OrderLogic.in_process_order_num.decrementAndGet();
        }else{

            OrderLogic.orderQueue.put(order);
            return false;
        }

        return true;

    }

}
