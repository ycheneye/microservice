package com.cy.mll_product.rabbitmq;

import com.cy.microserviceapi.result.MessageStatus;
import com.cy.mll_product.mapper.MllMessageMapper;
import com.cy.mll_product.service.MllProductService;
import com.rabbitmq.client.Channel;
import com.cy.microserviceapi.common.OrderProduct;
import com.cy.microserviceapi.dto.MllOrderDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.amqp.support.converter.MessageConverter;

import java.io.IOException;

/**
 * @作者 chenyi
 * @date 2019/5/30 10:15
 */
@Component
@Slf4j
public class OrderProductListener {

    @Autowired
    private MllProductService productService;
    @Autowired
    private MllMessageMapper mllMessageMapper;

    //监听的队列配置。监听到消息后，交给 @RabbitHandler修饰的方法处理
    @RabbitListener(
            bindings = @QueueBinding(
                    //申明队列
                    value=@Queue(value = OrderProduct.ORDER_TO_PRODUCT_QUEUE,durable = "true"),
                    //申明交换机,指定主题模式
                    exchange=@Exchange(value=OrderProduct.ORDER_TO_PRODUCT_EXCHANGE,durable = "true",type = OrderProduct.TOPIC
                            //忽略一些基本的申明时会出现的异常
                            ,ignoreDeclarationExceptions = "true"),
                    //路由键申明 topic模式 使用#表示任意多个
                    key=OrderProduct.ORDER_PRODUCT_ROUTINGKEY
            )

    )
    @RabbitHandler
    public void receiverMessage(Message message , MllOrderDTO mllOrderDTO, Channel channel) throws IOException {
        String id = message.getMessageProperties().getMessageId();
        log.info("获得的消息的id为：{}", id);
        log.info("获得消息的内容为：{}",mllOrderDTO);
        //首先取得消息的编号DELIVERY_TAG
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        log.info("获得消息的DELIVERY_TAG为：{}",deliveryTag);

        int i = mllMessageMapper.updateConsumerAndStatus(id, MessageStatus.GET_SUCCESS.getCode());
        if (i==1){
            try{
                productService.stockRollback(mllOrderDTO);
                mllMessageMapper.updateConsumerAndStatusSuccess(id, MessageStatus.FINISH.getCode());
            }catch (Exception e){
                mllMessageMapper.updateConsumerAndStatus(id, MessageStatus.GET_ERROR.getCode());
                log.error("消息在消费时出现异常,消息id: {} 异常信息：{}", id ,e);
                channel.basicAck(deliveryTag,false);
                throw new  RuntimeException(e);

            }
        }else {
            mllMessageMapper.updateStatusByConsumerStatus(id,MessageStatus.FINISH.getCode());
        }
        channel.basicAck(deliveryTag,false);
    }
    @Bean
    public MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }
}

