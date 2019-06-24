package com.cy.mll_order.rabbitMq;

import com.cy.microserviceapi.dto.MllOrderDTO;
import com.cy.microserviceapi.entity.MllMessage;
import com.cy.microserviceapi.result.MessageStatus;
import com.cy.microserviceapi.util.JsonMapper;
import com.cy.mll_order.mapper.MllMessageMapper;
import com.cy.mll_order.util.RabbitSenderUtil;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @作者 chenyi
 * @date 2019/5/31 17:29
 */
@EnableScheduling
@Slf4j
@Component
public class RabbitSenderTask {
    @Autowired
    private MllMessageMapper messageMapper;
    @Autowired
    private RabbitSenderUtil senderUtil;

    @Scheduled(cron = "*/5 * * * * ?")
    private void task(){
        List<MllMessage> messages = messageMapper.queryByStatus(MessageStatus.SEND_ERROR.getCode());
        for (MllMessage message: messages) {

            if (message.getSendCount()>=3){
                log.error("在订单业务失败通过rabbitmq通知商品微服务回滚操作中，消息发送失败次数超过三次，消息id：{}" + "，消息内容：{}，消息发送次数为：{}",
                        message.getMessageId(),message.getMessageBody(),message.getSendCount());
                //修改状态为失败 失败就是另外一套逻辑了 这个时候一般都需要人工干预了
                messageMapper.updateStatusById(MessageStatus.FAIL.getCode(),message.getMessageId());
                continue;
            }

            //重新发一次
            MllOrderDTO mllOrderDTO = JsonMapper.string2Obj(message.getMessageBody(), new TypeReference<MllOrderDTO>() {});
            senderUtil.orderErrorToProduct(message.getMessageId(), mllOrderDTO);

        }
    }
}
