package com.cy.microserviceapi.result;

import lombok.Getter;

/**
 * @作者 chenyi
 * @date 2019/4/16 15:16
 */

@Getter
public enum ResultEnum {
    SUCCESS(200,"成功"),
    FAIL(500,"失败");

    private int code;
    private String message;

    ResultEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
