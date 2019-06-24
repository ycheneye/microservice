package com.cy.mll_seckill.mapper;

import com.cy.mll_seckill.entity.MllSeckillOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @作者 chenyi
 * @date 2019/6/3 14:01
 */
@Mapper
@Repository
public interface MiaoshaOrderMapper {
    int queryByUserIdAndProductId(@Param("productId") long productId, @Param("userId") String userId);

    int insert(MllSeckillOrder seckillOrder);

}
