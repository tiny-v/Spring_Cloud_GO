package com.tinyv.sc.driver.core;
import java.util.concurrent.*;
import	java.util.concurrent.atomic.AtomicInteger;
import com.tiny.sc.core.domain.Constants;
import com.tiny.sc.core.domain.Driver;
import com.tiny.sc.core.domain.Order;
import com.tiny.sc.core.domain.Point;
import org.springframework.stereotype.Component;
import java.text.DecimalFormat;

/**
 * @author mayue
 */
@Component
public class OrderLogic {

    /** 从消息队列中，读取订单存入队列中 */
    public static LinkedBlockingQueue<Order> orderQueue = new LinkedBlockingQueue<>(20480);
    /**  */
    public static ConcurrentHashMap<String, Order> orderMap = new ConcurrentHashMap<>(20480);
    /** 所有的空闲的司机，进入队列  */
    public static LinkedBlockingQueue<Driver> driversQueue = new LinkedBlockingQueue<>(20480);
    /** 存放所有的司机信息 */
    public static ConcurrentHashMap<String, Driver> driverMap = new ConcurrentHashMap<>(20480);

    public static ThreadPoolExecutor executor = LocalThreadPool.Instance.executor;

    public static final DecimalFormat df = new DecimalFormat("#");
    /** 已结束订单的数量 */
    public static AtomicInteger finished_order_num = new AtomicInteger(0);
    /** 正在处理中订单的数量 */
    public static AtomicInteger in_process_order_num = new AtomicInteger(0);
    /** 总订单的数量 */
    public static AtomicInteger total_order_num = new AtomicInteger(0);

    /**
     * 计算司机和乘客间的距离
     * @param driverPoint
     * @param order
     * @return
     */
    public static double getDistanceToPassenger(Point driverPoint, Order order){
        Point passengerStartPoint = order.getStartPoint().getPoint();
        double distance_X = Math.pow((driverPoint.getPoint_X()-passengerStartPoint.getPoint_X()),2);
        double distance_Y = Math.pow((driverPoint.getPoint_Y()-passengerStartPoint.getPoint_Y()),2);
        return Math.sqrt(distance_X + distance_Y);
    }

    /**
     * 处理订单的耗时
     * @param driverPoint
     * @param order
     * @return
     */
    public static long getOrderCostTime(Point driverPoint, Order order){
        // 到达乘客的距离
        double reachDistance = getDistanceToPassenger(driverPoint, order);
        // 订单起点与终点的距离
        Point startPoint = order.getStartPoint().getPoint();
        Point endPoint = order.getEndPoint().getPoint();
        double orderDistance = Math.sqrt(Math.pow((endPoint.getPoint_X()-startPoint.getPoint_X()),2) + Math.pow((endPoint.getPoint_Y()-startPoint.getPoint_Y()),2));
        // 计算总耗时
        long consumerTime = Long.parseLong(df.format((reachDistance+orderDistance)*1000/ Constants.DRIVER_SPEED));
        return consumerTime;
    }

    public static boolean processOrder(Order order) throws InterruptedException {
        Driver driver = OrderLogic.driverMap.get(order.getDriverId());
        if(driver!=null && driver.getGrabOrder()!=null){
            OrderLogic.in_process_order_num.addAndGet(1);
            // todo：调用passenger服务，更新乘客状态为已接单
            // 计算订单耗时
            long consumerTime = OrderLogic.getOrderCostTime(driver.getLocation().getPoint(), driver.getGrabOrder());
            // 当前线程沉睡
            Thread.sleep(consumerTime);
            // 订单结束后, 更新司机位置
            driver.setLocation(driver.getGrabOrder().getEndPoint());
            order.setStatus(Constants.ORDER_STATUS.DONE.name());
            OrderLogic.finished_order_num.incrementAndGet();
            OrderLogic.orderMap.remove(order.getOrderId());
            driver.setStatus(Constants.DRIVER_STATUS.FREE.name());
            OrderLogic.driversQueue.put(driver);
            OrderLogic.in_process_order_num.decrementAndGet();
        }else{
            OrderLogic.orderQueue.put(order);
            return false;
        }
        return true;
    }

    /**
     * 通过订单初始位置，和司机的位置去判断二者是否匹配
     * @param driver
     * @param order
     * @return
     */
    public static boolean suitable(Driver driver, Order order){
        return driver.getLocation().getRegion().equals(order.getStartPoint().getRegion());
    }

}
