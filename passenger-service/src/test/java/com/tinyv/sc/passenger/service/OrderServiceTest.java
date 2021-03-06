package com.tinyv.sc.passenger.service;

import com.tiny.sc.core.domain.Passenger;
import com.tiny.sc.core.utils.LocationUtils;
import com.tinyv.sc.passenger.PassengerApplication;
import com.tinyv.sc.passenger.service.impl.OrderServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes={PassengerApplication.class, OrderServiceImpl.class})
public class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Test
    public void sendOrderTest(){
        Passenger passenger = new Passenger();
        passenger.setPassengerId("123458909");
        passenger.setLocation(LocationUtils.getRanDomLocation());
        orderService.sendOrder(passenger);
    }
}
