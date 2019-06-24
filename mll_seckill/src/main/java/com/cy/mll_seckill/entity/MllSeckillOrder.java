package com.cy.mll_seckill.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @作者 chenyi
 * @date 2019/6/3 9:42
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MllSeckillOrder implements Serializable {
    private long orderId;
    private BigDecimal payment;
    private long productId;
    private String userId;
    private int status;
}
