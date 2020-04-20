package com.tinyv.sc.driver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author mayue
 * @date 2020/04/20
 */
@SpringBootApplication
@EnableDiscoveryClient
public class DriverApplication {

    public static void main(String[] args){
            SpringApplication.run(DriverApplication.class, args);
    }

}
