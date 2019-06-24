package com.cy.microserviceapi.result;

import lombok.Getter;

/**
 * @作者 chenyi
 * @date 2019/6/3 10:19
 */
@Getter
public enum SeckillEnum {
    NONE_BEGIN(0,"秒杀还未开始"),
    KILLING(1,"秒杀正在进行"),
    ALREADY_DONE(2,"秒杀已经结束");

    private int code;
    private String message;

    SeckillEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
