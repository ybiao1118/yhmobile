package com.yinhai.rabbitMq.entity;

import lombok.Data;
import org.springframework.amqp.rabbit.connection.CorrelationData;

/**
 * @author yanbiao
 * @since 2020/4/9 15:50
 */
@Data
public class MyCorrelationData extends CorrelationData {
    //消息体
    private volatile Object message;
    //交换机
    private String exchange;
    //路由键
    private String routingKey;
    //重试次数
    private int retryCount = 0;
}
