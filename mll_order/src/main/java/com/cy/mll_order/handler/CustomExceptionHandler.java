package com.cy.mll_order.handler;

import com.cy.microserviceapi.exception.CustomException;
import com.cy.microserviceapi.result.ResultEnum;
import com.cy.microserviceapi.result.ResultResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @作者 chenyi
 * @date 2019/5/28 19:25
 * 自定义的全局异常处理器
 */
@Slf4j
@ControllerAdvice
@RestController
public class CustomExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResultResponse<ResultEnum> exceptionHandler(HttpServletRequest request, HttpServletResponse response,Exception e){
        log.error("请求为：{}   异常为："+e, request.getRequestURI());
        if (e instanceof CustomException){
            CustomException exception = (CustomException) e;
            return ResultResponse.fail(exception.getMessage());
        }
        return ResultResponse.fail(e.getMessage());
    }
}
