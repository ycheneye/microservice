package com.cy.microserviceapi.result;

import lombok.Getter;

/**
 * @作者 chenyi
 * @date 2019/5/31 15:02
 */
@Getter
public enum MessageStatus{
    //    1.已发送 2.已接收 3.已完成 4.消息发送异常 5.消息消费异常 0.消息未消费
    SEND_SUCCESS(1,"消息发送成功"),
    GET_SUCCESS(2,"消息接收成功"),
    FINISH(3,"消息完成"),
    SEND_ERROR(4,"消息发送异常"),
    GET_ERROR(5,"消息消费异常"),
    FAIL(6,"消息发送失败"),
    NOT_GET(0,"消息未消费");

    private int code;
    private String msg;

    MessageStatus(int code,String msg){
        this.code = code;
        this.msg = msg;
    }
}
