package com.cy.mll_product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MllProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(MllProductApplication.class, args);
    }

}
