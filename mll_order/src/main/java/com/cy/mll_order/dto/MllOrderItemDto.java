package com.cy.mll_order.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @作者 chenyi
 * @date 2019/5/28 11:13
 */
@Data
public class MllOrderItemDto implements Serializable {
    //商品id
    @NotNull(message = "商品id不能为null")
    private Long productId;

    @NotNull(message = "商品数量不能为空")
    @Min(value = 1,message = "数量不能少于一件")
    private Integer quantity;

}
