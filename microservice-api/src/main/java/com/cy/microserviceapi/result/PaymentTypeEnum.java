package com.cy.microserviceapi.result;

import lombok.Getter;

/**
 * @作者 chenyi
 * @date 2019/5/27 20:51
 */
@Getter
public enum PaymentTypeEnum {
    PAYMENT_ONLINE(1,"在线支付"),
    PRODUCT_OFFLINE(2,"货到付款");

    private int code;
    private String msg;

    PaymentTypeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
