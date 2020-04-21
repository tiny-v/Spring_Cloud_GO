package com.tinyv.sc.passenger;

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
@ComponentScan(basePackages = "com.tinyv.sc.passenger.*")
public class PassengerApplication {

    public static void main(String[] args){
            SpringApplication.run(PassengerApplication.class, args);
    }

}
