package com.cy.microserviceapi.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @作者 chenyi
 * @date 2019/4/16 15:13
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultResponse<T> {
    private int code;
    private String msg;
    private T data;

    public ResultResponse(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static ResultResponse fail(){
        return new ResultResponse(ResultEnum.FAIL.getCode(), ResultEnum.FAIL.getMessage());
    }

    public static ResultResponse fail(String msg){
        return new ResultResponse(ResultEnum.FAIL.getCode(), msg);
    }

    public static <T>ResultResponse fail(String msg,T data){
        return new ResultResponse(ResultEnum.FAIL.getCode(), msg ,data);
    }

    public static <T>ResultResponse fail(T data){
        return new ResultResponse(ResultEnum.FAIL.getCode(), ResultEnum.FAIL.getMessage() ,data);
    }

    public static ResultResponse success(){
        return new ResultResponse(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMessage());
    }

    public static <T>ResultResponse success(T data){
        return new ResultResponse(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMessage() ,data);
    }
}
