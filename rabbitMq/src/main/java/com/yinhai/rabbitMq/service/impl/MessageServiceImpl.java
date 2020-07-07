package com.yinhai.rabbitMq.service.impl;

import com.rabbitmq.client.Channel;
import com.yinhai.rabbitMq.config.RabbitConfig;
import com.yinhai.rabbitMq.entity.MyCorrelationData;
import com.yinhai.rabbitMq.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @author yanbiao
 * @since 2020/4/10 13:56
 */
@Service
public class MessageServiceImpl implements MessageService,
        RabbitTemplate.ConfirmCallback,RabbitTemplate.ReturnCallback {
    private  final Logger logger= LoggerFactory.getLogger(this.getClass());
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    public MessageServiceImpl(RabbitTemplate rabbitTemplate){
        this.rabbitTemplate=rabbitTemplate;
        rabbitTemplate.setReturnCallback(this::returnedMessage);
        rabbitTemplate.setConfirmCallback(this::confirm);
    }

    @Override
    public void sendMsg(String msg) {
        MyCorrelationData myCorrelationData=new MyCorrelationData();
        myCorrelationData.setId(UUID.randomUUID().toString());
        myCorrelationData.setExchange(RabbitConfig.EXCHANGE_A);
        myCorrelationData.setRoutingKey(RabbitConfig.ROUTINGKEY_A);
        myCorrelationData.setMessage(msg);
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_A,RabbitConfig.ROUTINGKEY_A,msg,myCorrelationData);

    }

    @Override
    public void sendDelayMsg(String msg, int delayTime) {
        MyCorrelationData myCorrelationData=new MyCorrelationData();
        myCorrelationData.setId(UUID.randomUUID().toString());
        myCorrelationData.setExchange(RabbitConfig.DEAD_LETTER_EXCHANGE);
        myCorrelationData.setRoutingKey(RabbitConfig.DELAY_ROUTING_KEY);
        myCorrelationData.setMessage(msg);
        System.out.println("msg="+",delayTime=" + delayTime);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        rabbitTemplate.convertAndSend(RabbitConfig.DEAD_LETTER_EXCHANGE,RabbitConfig.DELAY_ROUTING_KEY,msg,message -> {
            message.getMessageProperties().setExpiration(delayTime*1000 + "");
            System.out.println(sdf.format(new Date()) + " Delay send.");
            return message;
        },myCorrelationData);

    }


    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if (ack) {
            logger.info("消息发送成功:correlationData({}),ack({}),cause({})", correlationData, ack, cause);
        } else {
            logger.info("消息消费失败:" + cause);
            //重发消息
            if (correlationData instanceof com.yinhai.rabbitMq.entity.MyCorrelationData) {
                MyCorrelationData messageCorrelationData = (com.yinhai.rabbitMq.entity.MyCorrelationData) correlationData;
                String exchange = messageCorrelationData.getExchange();
                Object message = messageCorrelationData.getMessage();
                String routingKey = messageCorrelationData.getRoutingKey();
                int retryCount = messageCorrelationData.getRetryCount();
                //重试次数+1
                ((com.yinhai.rabbitMq.entity.MyCorrelationData) correlationData).setRetryCount(retryCount + 1);
                rabbitTemplate.convertSendAndReceive(exchange, routingKey, message, correlationData);
            }
        }

    }

    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        logger.info("消息丢失:exchange({}),route({}),replyCode({}),replyText({}),message:{}", exchange, routingKey, replyCode, replyText, message);
    }

    /**
     * 监听接受消息
     * @param message
     * @param channel
     * @throws IOException
     */
    @RabbitListener(queues = {RabbitConfig.QUEUE_A})
    public void process(Message message, Channel channel) throws IOException {
        // 采用手动应答模式, 手动确认应答更为安全稳定
        long tag=message.getMessageProperties().getDeliveryTag();
        logger.info("tag====: " + tag);
        logger.info("接收处理队列A当中的消息: " + new String(message.getBody()));
        channel.basicAck(tag, false);
    }
}
