package com.cy.mll_order.mapper;

import com.cy.microserviceapi.entity.MllOrderItem;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @作者 chenyi
 * @date 2019/5/29 10:26
 */
@Mapper
@Repository
public interface MllOrderItemMapper {
    int insertOrderItem(MllOrderItem mllOrderItem);
}
