package com.tinyv.sc.driver.core;
import com.tiny.sc.core.domain.Driver;
import com.tiny.sc.core.domain.Order;

import	java.util.concurrent.Semaphore;
import java.util.concurrent.Callable;

/**
 * @author mayue
 * @date 2020/04/21
 */
public class DriverThread implements Callable<Boolean> {

    private Driver driver;

    private Semaphore semaphore;

    private Order order;

    public DriverThread(Driver driver, Semaphore semaphore, Order order){
        this.driver = driver;
        this.semaphore = semaphore;
        this.order = order;
    }

    @Override
    public Boolean call() throws Exception {
        semaphore.acquire(1);
        if(order.getStatus().equals("FREE") && driver.getStatus().equals("FREE")){
            boolean suit = OrderLogic.suitable(driver, order);
            if(suit && order.getStatus().equals("FREE") && driver.getStatus().equals("FREE")){
                order.setStatus("ACCEPTED");
                order.setStatus("BUSY");
                driver.setGrabOrder(order);
                System.out.println("== 司机 " + driver.getName() + (driver.getGrabOrder()==null?"没有":"") + "抢到订单" );
            }
        }
        semaphore.release(1);
        if(driver.getGrabOrder()!=null){
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
            OrderLogic.finished_order_num.addAndGet(1);
            driver.setStatus("FREE");
        }else{
            return false;
        }
        return true;
    }

}
