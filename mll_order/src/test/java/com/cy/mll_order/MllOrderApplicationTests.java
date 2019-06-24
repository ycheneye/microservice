package com.cy.mll_order;

import com.cy.microserviceapi.entity.MllOrder;
import com.cy.microserviceapi.util.JsonMapper;
import com.cy.mll_order.dto.MllOrderItemDto;
import com.cy.mll_order.mapper.MllOrderMapper;
import com.cy.mll_order.service.MllOrderService;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class MllOrderApplicationTests {

    @Autowired
    private MllOrderService mllOrderService;

    @Test
    public void contextLoads() {
        ArrayList<MllOrderItemDto> orderItemDtos = Lists.newArrayList();
        MllOrderItemDto itemDto = new MllOrderItemDto();
        itemDto.setProductId(Long.valueOf(82));
        itemDto.setQuantity(45);
        MllOrderItemDto itemDto2 = new MllOrderItemDto();
        itemDto2.setProductId(Long.valueOf(5345));
        itemDto2.setQuantity(3);
        orderItemDtos.add(itemDto);
        orderItemDtos.add(itemDto2);

        String s = JsonMapper.obj2String(orderItemDtos);
        System.out.println(s);
    }


    @Test
    public void test(){
        ArrayList<MllOrderItemDto> list = Lists.newArrayList();
        MllOrderItemDto orderItemDto = new MllOrderItemDto();
        orderItemDto.setProductId(Long.valueOf(536563));
        orderItemDto.setQuantity(20);
        list.add(orderItemDto);
        mllOrderService.createOrder(list);
    }
}
