package com.tinyv.sc.passenger.service.impl;
import com.alibaba.fastjson.JSON;
import com.tinyv.sc.passenger.domain.Driver;
import com.tinyv.sc.passenger.domain.Order;
import com.tinyv.sc.passenger.domain.Passenger;
import com.tinyv.sc.passenger.global.Constants;
import com.tinyv.sc.passenger.service.OrderService;
import com.tinyv.sc.passenger.utils.DateTimeUtil;
import com.tinyv.sc.passenger.utils.LocationUtils;
import com.tinyv.sc.passenger.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.util.Date;

/**
 * @author mayue
 * @date 2020/04/20
 */
@Service
public class OrderServiceImpl implements OrderService {


    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void sendOrder(Passenger passenger) {
        Order order = new Order();
        //
        order.setOrderId(UUIDUtils.getUUID32());
        //设置出发位置
        order.setStartPoint(passenger.getLocation());
        //设置终点位置
        order.setEndPoint(LocationUtils.getRanDomLocation());
        //设置乘车人电话号码
        order.setPhoneNumber(passenger.getTelephone());
        //初始化为发送状态
        order.setStatus(Constants.ORDER_STATUS.SENDING.name());
        //
        order.setCreateTime(DateTimeUtil.getCurrentTime(new Date(), "yyyy-MM-dd HH:mm:ss"));

        //redisTemplate.opsForValue().set(order.getOrderId()+"_"+i,  JSON.toJSONString(order));
        redisTemplate.convertAndSend("Order", JSON.toJSONString(order));
    }

    @Override
    public void cancelOrder(Passenger passenger, String orderId) {

    }

    @Override
    public boolean receiveOrder(Driver driver, String orderId) {
        return false;
    }

    @Override
    public boolean processOrder(Driver driver, String orderId) {
        /**
         * todo
         *   1. 根据订单号查询订单
         *   2. 司机当前位置和订单起点地点的距离 + 订单起点到订单终点的距离
         *   3. 根据距离和默认车速计算需要耗时， 这段时间内，该线程不再能够接单
         *   4. 订单完成后， 司机位置为终点坐标
         * */
        return false;
    }

}
