package com.cy.mll_order.mapper;

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
    int insert(MllMessage mllMessage);

    void updateStatusById(@Param("status") int status,@Param("messageId") Long messageId);

    void updateSendCount(@Param("id") Long id, @Param("expire") Date expire, @Param("status") int status);

    MllMessage queryById(@Param("id") long id);

    List<MllMessage> queryByStatus(@Param("status") int status);

}
