package com.cy.microserviceapi.result;

import lombok.Getter;

/**
 * @作者 chenyi
 * @date 2019/5/27 20:47
 */
@Getter
public enum  ProductEnum {
    PRODUCT_NORMAL(1,"正常"),
    PRODUCT_DEL(3,"删除"),
    PRODUCT_DOWN(2,"下架"),
    PRODUCT_NOSTOCK(4,"库存不足");

    private int code;
    private String msg;

    ProductEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
