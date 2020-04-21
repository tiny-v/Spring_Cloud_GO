package com.tinyv.sc.passenger.service;

import com.tinyv.sc.passenger.PassengerApplication;
import com.tinyv.sc.passenger.domain.Passenger;
import com.tinyv.sc.passenger.service.impl.OrderServiceImpl;
import com.tinyv.sc.passenger.utils.LocationUtils;
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
        passenger.setTelephone(123458909);
        passenger.setLocation(LocationUtils.getRanDomLocation());
        orderService.sendOrder(passenger);
    }
}
