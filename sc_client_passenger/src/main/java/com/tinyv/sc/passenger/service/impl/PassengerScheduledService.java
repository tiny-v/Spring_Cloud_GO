package com.tinyv.sc.passenger.service.impl;

import java.util.*;
import	java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import com.alibaba.fastjson.JSON;
import com.tiny.sc.core.domain.Order;
import com.tiny.sc.core.domain.Passenger;
import com.tiny.sc.core.utils.DateTimeUtil;
import com.tiny.sc.core.utils.LocationUtils;
import com.tiny.sc.core.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import javax.annotation.PostConstruct;

/**
 * @author mayue
 */
@EnableScheduling
@Configuration
public class PassengerScheduledService {

    @Autowired
    private RedisTemplate redisTemplate;

    public static ConcurrentHashMap < String, Passenger> passengerMap = new ConcurrentHashMap<> ();

    public final int passenger_count = 100000;

    public final AtomicInteger send_order_count = new AtomicInteger(0);

    @Scheduled(fixedRate=3000)
    public void createOrder() {
        System.out.println("已经发送了: "+send_order_count.get()+" 个订单");
        Random random = new Random();
        Order order = null;
        List<Order> orderList = new ArrayList();
        for(Map.Entry entry: passengerMap.entrySet()){
            if(random.nextInt(passenger_count)<50){
                Passenger passenger = (Passenger)entry.getValue();
                if(passenger.getStatus().equals("FREE")){
                    order = new Order();
                    order.setOrderId(UUIDUtils.getUUID32());
                    order.setPhoneNumber(passenger.getTelephone());
                    order.setStartPoint(passenger.getLocation());
                    order.setEndPoint(LocationUtils.getRanDomLocation());
                    order.setStatus("SENDING");
                    order.setCreateTime(DateTimeUtil.getCurrentTime(new Date(), "yyyy-MM-dd HH:mm:ss"));
                    orderList.add(order);
                    redisTemplate.convertAndSend("Order", JSON.toJSONString(order));
                    send_order_count.addAndGet(1);
                }
            }
        }
    }

    /**
     * 初始化 十万个 乘客
     */
    @PostConstruct
    public void initPassenger(){
        Passenger passenger = null;
        for(int i=1; i<=passenger_count; i++){
            passenger = new Passenger();
            passenger.setTelephone(i);
            passenger.setPassengerId(i+"");
            passenger.setLocation(LocationUtils.getRanDomLocation());
            passenger.setStatus("FREE");
            passengerMap.put(passenger.getPassengerId(), passenger);
        }
    }

}
