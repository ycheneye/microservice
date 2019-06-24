package com.cy.microserviceapi.exception;

/**
 * @作者 chenyi
 * @date 2019/5/28 15:12
 */
public class CustomException extends RuntimeException {
    public CustomException(String msg){
        super(msg);
    }
}
