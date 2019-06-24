package com.cy.mll_order.mapper;

import com.cy.microserviceapi.entity.MllOrder;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @作者 chenyi
 * @date 2019/5/28 10:53
 */
@Mapper
@Repository
public interface MllOrderMapper {
    int insertOrder(MllOrder mllOrder);

    List<MllOrder> findAll();
}
