package com.tinyv.sc.passenger.service.impl;
import com.alibaba.fastjson.JSON;
import com.tiny.sc.core.domain.Constants;
import com.tiny.sc.core.domain.Order;
import com.tiny.sc.core.domain.Passenger;
import com.tiny.sc.core.utils.DateTimeUtil;
import com.tiny.sc.core.utils.LocationUtils;
import com.tiny.sc.core.utils.UUIDUtils;
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
        // 设置订单ID
        order.setOrderId(UUIDUtils.getUUID32());
        //设置出发位置
        order.setStartPoint(passenger.getLocation());
        //设置终点位置
        order.setEndPoint(LocationUtils.getRanDomLocation());
        //设置乘车人电话号码
        order.setPassengerId(passenger.getPassengerId());
        //初始化为发送状态
        order.setStatus(Constants.ORDER_STATUS.SENDING.name());
        //设置订单创建时间
        order.setCreateTime(DateTimeUtil.getCurrentTime(new Date(), "yyyy-MM-dd HH:mm:ss"));
        //将订单写入数据库
        redisTemplate.opsForValue().set(order.getOrderId(), JSON.toJSONString(order));
        //将订单通过redis消息队列发送到driver端
        redisTemplate.convertAndSend(Constants.MQ_TOPIC_SENDING_ORDER, JSON.toJSONString(order));
    }

    @Override
    public void cancelOrder(Passenger passenger, String orderId) {

    }


}
