package com.tinyv.sc.driver;

import com.tiny.sc.core.domain.Driver;
import com.tiny.sc.core.utils.LocationUtils;
import com.tiny.sc.core.utils.UUIDUtils;
import com.tinyv.sc.driver.service.DriverService;
import com.tinyv.sc.driver.service.impl.DriverServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes={DriverApplication.class, DriverServiceImpl.class})
public class DriverServiceTest {

    @Autowired
    private DriverService driverService;

    @Test
    public void grabOrderTest(){
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
