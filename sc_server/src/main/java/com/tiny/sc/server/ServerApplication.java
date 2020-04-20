package com.tiny.sc.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author TinyV
 * @date 2020/4/18
 */
@SpringBootApplication
@EnableEurekaServer
public class ServerApplication {

    public static void main(String[] args){
        SpringApplication.run(ServerApplication.class, args);
    }

}
