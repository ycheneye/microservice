package com.cy.mll_seckill.mapper;

import com.cy.mll_seckill.entity.MllSeckillProduct;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @作者 chenyi
 * @date 2019/6/3 10:00
 */
@Mapper
@Repository
public interface MllSeckillProductMapper {
    MllSeckillProduct findByProductId(@Param("productId") Long productId);

    int updateStockById(long productId);
}
