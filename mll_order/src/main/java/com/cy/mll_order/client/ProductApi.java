package com.cy.mll_order.client;

import com.cy.microserviceapi.dto.MllOrderDTO;
import com.cy.microserviceapi.result.ResultResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

/**
 * @作者 chenyi
 * @date 2019/5/29 10:20
 */
@FeignClient(url = "http://www.mll.com:9001",name = "product")
@Component
public interface ProductApi{
    @PostMapping("product/updateStock")
    ResultResponse<MllOrderDTO> updateStock(@RequestBody MllOrderDTO orderDTO);
}
