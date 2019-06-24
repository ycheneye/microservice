package com.cy.microserviceapi.dto;

import com.cy.microserviceapi.entity.MllOrderItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @作者 chenyi
 * @date 2019/5/27 20:41
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MllOrderDTO implements Serializable {
    //订单id
    @NotNull(message = "订单id不能为空")
    private Long orderId;
    //订单总价格 不考虑邮费
    private BigDecimal payment;

    //订单项
    @Valid//嵌套参数验证
    private List<MllOrderItem> mllOrderItemList;

}
