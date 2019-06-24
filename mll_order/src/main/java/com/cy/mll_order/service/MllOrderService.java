package com.cy.mll_order.service;

import com.cy.microserviceapi.dto.MllOrderDTO;
import com.cy.microserviceapi.entity.MllOrder;
import com.cy.microserviceapi.entity.MllOrderItem;
import com.cy.microserviceapi.exception.CustomException;
import com.cy.microserviceapi.result.OrderStatusEnum;
import com.cy.microserviceapi.result.ResultEnum;
import com.cy.microserviceapi.result.ResultResponse;
import com.cy.microserviceapi.util.BigDecimalUtil;
import com.cy.microserviceapi.util.JsonMapper;
import com.cy.microserviceapi.util.SnowFlake;
import com.cy.mll_order.dto.MllOrderItemDto;
import com.cy.mll_order.mapper.MllOrderItemMapper;
import com.cy.mll_order.mapper.MllOrderMapper;
import com.cy.mll_order.util.RabbitSenderUtil;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @作者 chenyi
 * @date 2019/5/29 10:41
 */
@Service
@Slf4j
public class MllOrderService {

    @Autowired
    private SnowFlake snowFlake;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private MllOrderMapper orderMapper;

    @Autowired
    private MllOrderItemMapper orderItemMapper;

    @Autowired
    private RabbitSenderUtil senderUtil;

    /**
     * 生成订单的同时，采用商品微服务接口削减库存
     * @param orderItemDtoList
     * @return
     */
    @Transactional
    public ResultResponse<MllOrderDTO> createOrder(List<MllOrderItemDto> orderItemDtoList){
        MllOrderDTO orderDTO = new MllOrderDTO();
        long orderId = snowFlake.nextId();
        orderDTO.setOrderId(orderId);

        ArrayList<MllOrderItem> orderItemList = Lists.newArrayList();
        for (MllOrderItemDto orderItemDto:orderItemDtoList) {
            MllOrderItem orderItem = new MllOrderItem();
            orderItem.setOrderId(orderId);
            orderItem.setItemId(orderItemDto.getProductId());
            orderItem.setNum(orderItemDto.getQuantity());
            orderItemList.add(orderItem);
        }
        orderDTO.setMllOrderItemList(orderItemList);
        ResponseEntity responseEntity = restTemplate.postForEntity("http://product/product/updateStock", orderDTO, ResultResponse.class);
        ResultResponse<MllOrderDTO> resultResponse = (ResultResponse<MllOrderDTO>) responseEntity.getBody();
        if (resultResponse.getCode()== ResultEnum.FAIL.getCode()){
            log.error("削减商品库存失败，原因：{}", resultResponse.getMsg());
            throw new CustomException(resultResponse.getMsg());
        }
        Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
        String json = gson.toJson(resultResponse.getData());
        MllOrderDTO mllOrderDTO = JsonMapper.string2Obj(json, new TypeReference<MllOrderDTO>() {});
        if (insertOrderByOrderDto(mllOrderDTO) <=0 ){
            throw new CustomException(OrderStatusEnum.ERROR.getMsg());
        }
        return ResultResponse.success(mllOrderDTO);
    }

    /**
     * 根据mllOrderDTO插入order，并完善order的信息
     * @param mllOrderDTO
     * @return
     */
    private int insertOrderByOrderDto(MllOrderDTO mllOrderDTO) {
        int i = 0;
        try {
            MllOrder order = new MllOrder();
            order.setOrderId(mllOrderDTO.getOrderId());
            List<MllOrderItem> orderItemList = mllOrderDTO.getMllOrderItemList();
            BigDecimal payment = new BigDecimal("0");
            for (MllOrderItem orderItem : orderItemList) {
                BigDecimalUtil.add(payment, orderItem.getTotalFee());
                insertOrderItem(orderItem);
            }
            order.setPayment(payment);
            order.setStatus(String.valueOf(OrderStatusEnum.ORDER_UNPAID.getCode()));
            order.setCreateTime(new Date());
            order.setUpdateTime(new Date());
            //todo:买家信息与卖家信息
            i = orderMapper.insertOrder(order);
            i = 1/0;
            return i;
        } catch (Exception e) {
            log.error("异常信息：{}", e.getMessage());
            senderUtil.orderErrorToProduct(snowFlake.nextId(), mllOrderDTO);
            throw new RuntimeException(e);
        }
    }

    private void insertOrderItem(MllOrderItem orderItem){
        orderItemMapper.insertOrderItem(orderItem);
    }
}
