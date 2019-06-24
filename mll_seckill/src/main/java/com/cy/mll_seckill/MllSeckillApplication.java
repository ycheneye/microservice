package com.cy.mll_seckill;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MllSeckillApplication {

    public static void main(String[] args) {
        SpringApplication.run(MllSeckillApplication.class, args);
    }

}
