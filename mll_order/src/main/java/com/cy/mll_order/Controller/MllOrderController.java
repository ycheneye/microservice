package com.cy.mll_order.Controller;

import com.cy.microserviceapi.result.ResultEnum;
import com.cy.microserviceapi.result.ResultResponse;
import com.cy.mll_order.dto.MllOrderItemDto;
import com.cy.mll_order.service.MllOrderService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * @作者 chenyi
 * @date 2019/5/28 11:29
 */
@RestController
@RequestMapping("order")
public class MllOrderController {

    @Autowired
    private MllOrderService orderService;

    @PostMapping("create")
    @HystrixCommand(fallbackMethod = "createOrderFallBack")
    public ResultResponse create(@Valid
                                 @RequestBody List<MllOrderItemDto> list){
        return orderService.createOrder(list);
    }

    private ResultResponse createOrderFallBack(List<MllOrderItemDto> list){
        return ResultResponse.fail(ResultEnum.FAIL.getMessage());
    }
}
