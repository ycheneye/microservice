package com.cy.microserviceapi.result;

import lombok.Getter;

/**
 * @作者 chenyi
 * @date 2019/5/28 10:12
 */
@Getter
public enum OrderResourceEnum {
    ORDER_RESOURCE_APP(1,"app端"),
    ORDER_RESOURCE_PC(2,"pc端"),
    ORDER_RESOURCE_M(3,"m端"),
    ORDER_RESOURCE_WECHAT(4,"微信端"),
    ORDER_RESOURCE_MOBILE(5,"移动app端");
    private int code;
    private String msg;

    OrderResourceEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
