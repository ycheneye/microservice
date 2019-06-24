package com.cy.mll_product.controller;

import com.cy.microserviceapi.dto.MllOrderDTO;
import com.cy.microserviceapi.result.ResultResponse;
import com.cy.mll_product.entity.MllProduct;
import com.cy.mll_product.mapper.MllProductMapper;
import com.cy.mll_product.service.MllProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @作者 chenyi
 * @date 2019/5/28 15:59
 */
@RestController
@RequestMapping("product")
@Slf4j
public class MllProductController{

    @Autowired
    private MllProductService productService;

    @Autowired
    private MllProductMapper productMapper;

    @PostMapping("updateStock")
    public ResultResponse updateStock(@RequestBody MllOrderDTO orderDTO){
        return productService.updateStock(orderDTO);
    }

    @GetMapping("{id}")
    public ResultResponse<MllProduct> findById(@PathVariable("id") Long id){
        return ResultResponse.success(productMapper.findById(id));
    }
}
