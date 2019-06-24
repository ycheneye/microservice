package com.cy.mll_product.mapper;

import com.cy.mll_product.entity.MllProduct;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @作者 chenyi
 * @date 2019/5/28 15:47
 */
@Mapper
@Repository
public interface MllProductMapper {
    MllProduct findById(@Param("productId") Long productId);

    int updateStockById(@Param("productId")Long productId ,@Param("num")Integer num);

    int RollBackStockById(@Param("productId")Long productId ,@Param("num")Integer num);
}
