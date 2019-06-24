package com.cy.microserviceapi.result;

import lombok.Getter;

/**
 * @作者 chenyi
 * @date 2019/5/27 20:54
 */
@Getter
public enum OrderStatusEnum {
    ORDER_UNPAID(1,"未付款"),
    ORDER_ALREADYPAID(2,"已付款"),
    ORDER_UNSHIPPED(3,"未发货"),
    ORDER_ALREADYSHIPPED(4,"已发货"),
    ORDER_SUCCESS(5,"交易成功"),
    ORDER_SHUTDOWN(6,"交易关闭"),
    ORDER_UNCOMMENT(7,"待评价"),
    ERROR(100,"订单异常"),
    PAY_ERROR(101,"订单支付异常"),
    PRODUCT_STOCK_ERROR(102,"商品库存扣减异常"),
    SEND_GOODS_ERROR(103,"发货异常");

    private int code;
    private String msg;

    OrderStatusEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
