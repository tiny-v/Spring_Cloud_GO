package com.tinyv.sc.driver.controller;

import com.tiny.sc.core.domain.Driver;
import com.tiny.sc.core.domain.Order;
import com.tiny.sc.core.utils.LocationUtils;
import com.tiny.sc.core.utils.UUIDUtils;
import com.tinyv.sc.driver.core.OrderLogic;
import com.tinyv.sc.driver.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mayue
 */
@RestController
@RequestMapping("/driver")
public class DriverController {

    @Autowired
    private DriverService driverService;

    @RequestMapping(value="/initOrder", method=RequestMethod.GET)
    public void initOrder() throws InterruptedException {
        Order order = null;
        //
        for(int i=0; i<20000; i++){
            order = new Order();
            //
            //设置乘车人电话号码
            order.setPassengerId(i+"");
            //初始化为发送状态
            order.setStatus("SENDING");
            order.setOrderId(UUIDUtils.getUUID32());
            //设置出发位置
            order.setStartPoint(LocationUtils.getRanDomLocation());
            //设置终点位置
            order.setEndPoint(LocationUtils.getRanDomLocation());
            OrderLogic.orderQueue.put(order);

        }
    }

    @RequestMapping(value="/grabOrder", method=RequestMethod.GET)
    public void grabOrder(){
        Driver driver = null;
        for(int i=0; i<10; i++){
            driver = new Driver();
            driver.setName("I"+i);
            driver.setDriverId(UUIDUtils.getUUID32());
            driver.setLocation(LocationUtils.getRanDomLocation());
            driver.setStatus("FREE");
            //driverService.grabOrder(driver);
        }

    }

}
