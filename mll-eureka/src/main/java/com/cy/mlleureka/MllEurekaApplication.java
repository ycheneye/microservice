package com.cy.mlleureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class MllEurekaApplication {

    public static void main(String[] args) {
        SpringApplication.run(MllEurekaApplication.class, args);
    }

}
