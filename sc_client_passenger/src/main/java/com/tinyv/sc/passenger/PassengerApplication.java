package com.tinyv.sc.passenger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author mayue
 * @date 2020/04/20
 */
@SpringBootApplication
@EnableDiscoveryClient
public class PassengerApplication {

    public static void main(String[] args){
            SpringApplication.run(PassengerApplication.class, args);
    }

}
