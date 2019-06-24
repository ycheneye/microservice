package com.cy.mll_seckill;

import com.cy.mll_seckill.entity.MllSeckillProduct;
import com.cy.mll_seckill.mapper.MllSeckillProductMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MllSeckillApplicationTests {
    @Autowired
    private MllSeckillProductMapper productMapper;
    @Test
    public void contextLoads() {
        MllSeckillProduct byProductId =
                productMapper.findByProductId(Long.valueOf(536563));
        System.out.println(byProductId.getStartDate().toLocaleString());
        System.out.println(byProductId.getStartDate());
    }

}
