package com.cy.mll_seckill.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @作者 chenyi
 * @date 2019/6/3 9:39
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MllSeckillProduct implements Serializable {
    private long id;
    private long productId;
    private String title;
    private BigDecimal price;
    private BigDecimal miaoshaPrice;
    private int stockCount;
    private String image;
    private Date startDate;
    private Date endDate;
    private String brand;
    private String spec;
}
