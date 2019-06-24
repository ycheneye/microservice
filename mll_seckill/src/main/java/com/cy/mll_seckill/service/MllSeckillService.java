package com.cy.mll_seckill.service;

import com.cy.microserviceapi.result.OrderStatusEnum;
import com.cy.microserviceapi.result.ProductEnum;
import com.cy.microserviceapi.result.ResultResponse;
import com.cy.microserviceapi.util.SnowFlake;
import com.cy.mll_seckill.entity.MllSeckillOrder;
import com.cy.mll_seckill.entity.MllSeckillProduct;
import com.cy.mll_seckill.mapper.MiaoshaOrderMapper;
import com.cy.mll_seckill.mapper.MllSeckillProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @作者 chenyi
 * @date 2019/6/3 10:12
 */
@Service
public class MllSeckillService {
    @Autowired
    private MllSeckillProductMapper seckillProductMapper;
    @Autowired
    private MiaoshaOrderMapper miaoshaOrderMapper;
    @Autowired
    private SnowFlake snowFlake;

    public ResultResponse<MllSeckillProduct> findByProdcutId(Long productId){
        return ResultResponse.success(seckillProductMapper.findByProductId(productId));
    }

    @Transactional
    public ResultResponse doSeckill(Long productId ,String userId){
        //判断库存是否充足
        ResultResponse<MllSeckillProduct> resultResponse = findByProdcutId(productId);
        MllSeckillProduct seckillProduct = resultResponse.getData();
        int stockCount = seckillProduct.getStockCount();
        if (stockCount<=0){
            return ResultResponse.fail(ProductEnum.PRODUCT_NOSTOCK.getMsg());
        }
        //判断是否重复秒杀
        int i = miaoshaOrderMapper.queryByUserIdAndProductId(productId, userId);
        if (i>0){
            return ResultResponse.fail("您已抢购过该商品，不能重复抢购！");
        }
        //减库存
        int stock = seckillProductMapper.updateStockById(productId);
        if (stock==0){
            return ResultResponse.fail(ProductEnum.PRODUCT_NOSTOCK.getMsg());
        }
        //create seckillOrder
        MllSeckillOrder seckillOrder = new MllSeckillOrder();
        seckillOrder.setOrderId(snowFlake.nextId());
        seckillOrder.setPayment(seckillProduct.getMiaoshaPrice());
        seckillOrder.setProductId(productId);
        seckillOrder.setUserId(userId);
        seckillOrder.setStatus(OrderStatusEnum.ORDER_UNPAID.getCode());
        miaoshaOrderMapper.insert(seckillOrder);
        return ResultResponse.success(seckillOrder);
    }
}
