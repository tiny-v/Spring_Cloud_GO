package com.tinyv.sc.passenger.service.impl;
import com.alibaba.fastjson.JSON;
import com.tiny.sc.core.domain.Order;
import com.tiny.sc.core.domain.Passenger;
import com.tiny.sc.core.utils.DateTimeUtil;
import com.tiny.sc.core.utils.LocationUtils;
import com.tiny.sc.core.utils.UUIDUtils;
import com.tinyv.sc.passenger.global.Constants;
import com.tinyv.sc.passenger.service.OrderService;
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

        redisTemplate.opsForValue().set(order.getOrderId(), JSON.toJSONString(order));
        redisTemplate.convertAndSend("Order", JSON.toJSONString(order));
    }

    @Override
    public void cancelOrder(Passenger passenger, String orderId) {

    }


}
