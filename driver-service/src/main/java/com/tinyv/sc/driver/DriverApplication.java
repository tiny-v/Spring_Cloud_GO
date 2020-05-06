package com.tinyv.sc.driver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author mayue
 * @date 2020/04/20
 */

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = "com.tinyv.sc.driver.*")
public class DriverApplication {

    public static void main(String[] args){
            SpringApplication.run(DriverApplication.class, args);
    }

}
