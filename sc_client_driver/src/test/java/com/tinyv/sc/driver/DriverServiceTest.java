package com.tinyv.sc.driver;

import com.tinyv.sc.driver.domain.Driver;
import com.tinyv.sc.driver.service.DriverService;
import com.tinyv.sc.driver.service.impl.DriverServiceImpl;
import com.tinyv.sc.driver.utils.LocationUtils;
import com.tinyv.sc.driver.utils.UUIDUtils;
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
