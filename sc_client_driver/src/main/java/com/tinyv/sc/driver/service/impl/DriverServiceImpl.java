package com.tinyv.sc.driver.service.impl;

import com.tiny.sc.core.domain.Order;
import com.tinyv.sc.driver.service.DriverService;
import org.springframework.stereotype.Service;

/**
 * @author mayue
 */
@Service
public class DriverServiceImpl implements DriverService {

    @Override
    public boolean arrangeOrder(Order order) {
        return false;
    }


    @Override
    public void scheduledGrabOrder() {
    }

}
