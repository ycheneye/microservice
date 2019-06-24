package com.cy.mll_product.mapper;

import com.cy.microserviceapi.entity.MllMessage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @作者 chenyi
 * @date 2019/5/31 15:07
 */
@Mapper
@Repository
public interface MllMessageMapper {
    int updateConsumerAndStatus(@Param("messageId") String messageId, @Param("status") int status);

    void updateConsumerAndStatusSuccess(@Param("messageId") String messageId, @Param("status") int status);

    int updateStatusByConsumerStatus(@Param("messageId") String messageId,@Param("status") int status);
}
