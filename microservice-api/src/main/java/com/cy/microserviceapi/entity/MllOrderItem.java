package com.cy.microserviceapi.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @作者 chenyi
 * @date 2019/5/27 20:39
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MllOrderItem implements Serializable {
    private Long id;

    private Long itemId;

    @NotNull(message = "商品id不能为空")
    private Long goodsId;

    private Long orderId;

    private String title;

    private BigDecimal price;

    @NotNull(message = "商品数量不能为空")
    @Min(value = 1,message = "数量不能少于一件")
    private Integer num;

    private BigDecimal totalFee;

    private String picPath;

    private String sellerId;

}
