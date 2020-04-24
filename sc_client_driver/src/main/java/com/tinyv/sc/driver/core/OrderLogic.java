package com.tinyv.sc.driver.core;
import	java.util.concurrent.atomic.AtomicInteger;

import com.tiny.sc.core.domain.Driver;
import com.tiny.sc.core.domain.Order;
import com.tiny.sc.core.domain.Point;
import org.springframework.stereotype.Component;
import java.text.DecimalFormat;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author mayue
 */
@Component
public class OrderLogic {

    /**  */
    public static LinkedBlockingQueue<Order> orderQueue = new LinkedBlockingQueue<>(20480);
    /**  */
    public static ConcurrentHashMap<String, Order> orderMap = new ConcurrentHashMap<>(20480);
    /**  */
    public static LinkedBlockingQueue<Driver> driversQueue = new LinkedBlockingQueue<>(20480);

    public static ConcurrentHashMap<String, LinkedBlockingQueue<Driver>> driverMap = new ConcurrentHashMap();

    public static ExecutorService executorService = Executors.newFixedThreadPool(200);

    /**  */
    public static final double DISTANCE_THRESHOLD = 200.0;

    public static final double DRIVER_SPEED = 4000.0;

    public static final DecimalFormat df = new DecimalFormat("#");
    /**  */
    public static AtomicInteger finished_order_num = new AtomicInteger(0);

    public static AtomicInteger in_process_order_num = new AtomicInteger(0);

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
     *
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
        long consumerTime = Long.parseLong(df.format((reachDistance+orderDistance)*1000/DRIVER_SPEED));
        return consumerTime;
    }

    public static synchronized boolean suitable(Driver driver, Order order){
        int driverPoint_X = driver.getLocation().getRegion_X();
        int driverPoint_Y = driver.getLocation().getRegion_Y();
        int orderPoint_X = order.getStartPoint().getRegion_X();
        int orderPoint_Y = order.getStartPoint().getRegion_Y();
        if(Math.abs(driverPoint_X-orderPoint_X)<=5 && Math.abs(driverPoint_Y-orderPoint_Y)<=5){
            return true;
        }
        return false;
    }


}
