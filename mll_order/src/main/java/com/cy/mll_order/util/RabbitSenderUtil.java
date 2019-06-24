package com.cy.mll_order.util;

import com.cy.microserviceapi.common.OrderProduct;
import com.cy.microserviceapi.dto.MllOrderDTO;
import com.cy.microserviceapi.entity.MllMessage;
import com.cy.microserviceapi.result.MessageStatus;
import com.cy.microserviceapi.util.DateUtil;
import com.cy.mll_order.mapper.MllMessageMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;

import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * @作者 chenyi
 * @date 2019/5/30 10:01
 */
@Component
@Slf4j
public class RabbitSenderUtil {
    //springboot自动注入的 rabbitmq的实例
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private MessageConverter messageConverter;
    @Autowired
    private MllMessageMapper messageMapper;
    //定义confirm 确认监听回调函数  是否成功将消息 发送到rabbitmq的broker 都会执行该方法
    final RabbitTemplate.ConfirmCallback confirmCallback = new RabbitTemplate.ConfirmCallback() {
        //先看第二个参数 就是消息是否发送到了broker的结果
        //第一个参数为传递的消息的信息 包含了全局唯一id
        @Override
        public void confirm(CorrelationData correlationData, boolean flag, String s) {
            log.info("confirm消息的id为：{}",correlationData.getId());
            log.info("发送是否成功：{}", flag);
            if (!flag){
                messageMapper.updateStatusById(MessageStatus.SEND_ERROR.getCode(), Long.valueOf(correlationData.getId()));
            }
        }
    };
    //定义return的监听回调函数  不能将消息路由到了指定queue 就会指定该回调方法
    final RabbitTemplate.ReturnCallback returnCallback = new RabbitTemplate.ReturnCallback() {
        /**
         * @param message   发送的消息
         * @param relayCode 错误码
         * @param relayText 错误文本信息
         * @param exchange  发送的交换机
         * @param routingKey 发送的路由键
         */
        @Override
        public void returnedMessage(Message message, int relayCode, String relayText,
                                    String exchange, String routingKey) {
            log.error("错误码错误码错误码错误码错误码错误码错误码错误码：{}", relayCode);
            log.error("错误信息：：{}", relayText);
            if (relayCode==312){
                messageMapper.updateStatusById(MessageStatus.SEND_ERROR.getCode(), Long.valueOf(message.getMessageProperties().getMessageId()));
            }
        }
    };

    public static final long EXPIRE = 60*1000;

    @Transactional(propagation= Propagation.REQUIRES_NEW)
    public void orderErrorToProduct(Long id, MllOrderDTO mllOrderDTO){
        //将该条消息的全局id设置进入CorrelationData方便confirm中取出来 唯一标识该消息
        CorrelationData correlationData = new CorrelationData(String.valueOf(id));
        MessageProperties messageProperties = new MessageProperties();
        //设置messageId 在消费端取出来可以判断 重复消费
        messageProperties.setMessageId(String.valueOf(id));
        Message message = messageConverter.toMessage(mllOrderDTO, messageProperties);

        try{
            int i = messageMapper.insert(MllMessage.builder().messageId(id).messageBody(new String(message.getBody())).consumeCount(0).expire(DateUtil.expire(EXPIRE))
                    .status(MessageStatus.SEND_SUCCESS.getCode()).sendCount(1).build());
            log.info("是否插入数据库成功：{}", i);
        }catch (Exception e){
            messageMapper.updateSendCount(id,DateUtil.expire(EXPIRE),MessageStatus.SEND_SUCCESS.getCode());
        }

        //加入消息确认模式的监听
        rabbitTemplate.setConfirmCallback(confirmCallback);
        //加入消息return模式的监听
        rabbitTemplate.setReturnCallback(returnCallback);
        //1.交换机名称  2.routingKey 3.发送的消息数据 4.该消息的信息 例如全局唯一的id设置6
        rabbitTemplate.convertAndSend(OrderProduct.ORDER_TO_PRODUCT_EXCHANGE,
                OrderProduct.ORDER_TO_PRODUCT_ROLLBACK, message,correlationData);

    }
    //消息转换器 使用同一类转换器 方便对象传输
    @Bean
    public MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }
}
