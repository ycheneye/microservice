package com.cy.mll_seckill.config;

import com.cy.microserviceapi.util.SnowFlake;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @作者 chenyi
 * @date 2019/5/28 15:41
 */
@Configuration
public class SnowflakeConfig {

    @Bean("snowFlake")
    public SnowFlake snowFlake(){
        return new SnowFlake(8, 16);
    }
}
