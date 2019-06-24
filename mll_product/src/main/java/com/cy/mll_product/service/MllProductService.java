package com.cy.mll_product.service;

import com.cy.microserviceapi.dto.MllOrderDTO;
import com.cy.microserviceapi.entity.MllOrderItem;
import com.cy.microserviceapi.exception.CustomException;
import com.cy.microserviceapi.result.ProductEnum;
import com.cy.microserviceapi.result.ResultResponse;
import com.cy.microserviceapi.util.BigDecimalUtil;
import com.cy.microserviceapi.util.SnowFlake;
import com.cy.mll_product.entity.MllProduct;
import com.cy.mll_product.mapper.MllProductMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.List;

/**
 * @作者 chenyi
 * @date 2019/5/28 15:46
 */
@Service
@Slf4j
public class MllProductService {

    @Autowired
    private MllProductMapper productMapper;

    @Autowired
    private SnowFlake snowFlake;

    @Transactional
    public ResultResponse updateStock(MllOrderDTO orderDTO){
        //获取订单项
        List<MllOrderItem> orderItemList = orderDTO.getMllOrderItemList();
        for (MllOrderItem orderItem:orderItemList) {
            //根据id查询商品
            ResultResponse<MllProduct> mllProductResultResponse = findById(orderItem.getItemId());
            MllProduct mllProduct = mllProductResultResponse.getData();
            if (mllProduct==null){
                log.error("所需扣除库存的商品id不存在，商品id: {}", orderItem.getItemId());
                throw new CustomException(ProductEnum.PRODUCT_DEL.getMsg());
            }
            int i = productMapper.updateStockById(mllProduct.getId(), orderItem.getNum());
            if (i<=0){
                log.error("修改库存失败，修改的商品id：{}", mllProduct.getId());
                throw new CustomException(ProductEnum.PRODUCT_NOSTOCK.getMsg());
            }
            orderItem.setId(snowFlake.nextId());
            //价格
            orderItem.setPrice(mllProduct.getPrice());
            //总价格
            orderItem.setTotalFee(BigDecimalUtil.multi(mllProduct.getPrice(),orderItem.getNum()));
            //标题
            orderItem.setTitle(mllProduct.getTitle());
            //图片
            orderItem.setPicPath(mllProduct.getImage());
            //属于哪个order的id
            orderItem.setOrderId(orderDTO.getOrderId());
        }
        return ResultResponse.success(orderDTO);
    }

    public ResultResponse<MllProduct> findById(Long id){
        MllProduct mllProduct = productMapper.findById(id);
        if (mllProduct==null){
            return ResultResponse.fail(ProductEnum.PRODUCT_DEL.getMsg());
        }
        return ResultResponse.success(mllProduct);
    }

    @Transactional
    public void stockRollback(MllOrderDTO mllOrderDTO){
        @Valid List<MllOrderItem> orderItemList = mllOrderDTO.getMllOrderItemList();
        for (MllOrderItem orderItem: orderItemList) {
            productMapper.RollBackStockById(orderItem.getItemId(), orderItem.getNum());
        }
    }
}
