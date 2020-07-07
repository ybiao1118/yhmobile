package com.yinhai.rabbitMq.config;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yanbiao
 * @since 2020/4/9 11:20
 */
@Configuration
public class RabbitConfig {



    @Value("${spring.rabbitmq.host}")
    private  String host;

    @Value("${spring.rabbitmq.port}")
    private  int  port;

    @Value("${spring.rabbitmq.password}")
    private  String password;

    @Value("${spring.rabbitmq.username}")
    private  String username;

    public static final String EXCHANGE_A = "my-mq-exchange_A";
    public static final String EXCHANGE_B = "my-mq-exchange_B";
    public static final String EXCHANGE_C = "my-mq-exchange_C";
    //延时消费的exchange
    public static final String DEAD_LETTER_EXCHANGE = "exchange.demo.delay";

    public static final String QUEUE_A = "QUEUE_A";
    public static final String QUEUE_B = "QUEUE_B";
    public static final String QUEUE_C = "QUEUE_C";
    //延时消费的队列名称
    public static final String DELAY_QUEUE= "queue.demo.delay";

    public static final String ROUTINGKEY_A = "spring-boot-routingKey_A";
    public static final String ROUTINGKEY_B = "spring-boot-routingKey_B";
    //延时消费的routing-key名称
    public static final String DELAY_ROUTING_KEY = "routingkey.demo.delay";

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(host,port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost("/");
        connectionFactory.setPublisherConfirms(true);
        return connectionFactory;
    }
    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate template = new RabbitTemplate(connectionFactory());
        return template;
    }
    /**
     * 针对消费者配置
     * 1. 设置交换机类型
     * 2. 将队列绑定到交换机
     FanoutExchange: 将消息分发到所有的绑定队列，无routingkey的概念
     HeadersExchange ：通过添加属性key-value匹配
     DirectExchange:按照routingkey分发到指定队列
     TopicExchange:多关键字匹配
     */
    @Bean
    public DirectExchange defaultExchange(){
        return new DirectExchange(EXCHANGE_A);
    }
    @Bean
    public DirectExchange deadLetterExchange(){
        return new DirectExchange(DEAD_LETTER_EXCHANGE, true, false);
    }
    @Bean
    public Queue queueA(){
        return new Queue(QUEUE_A,true);//队列持久
    }
    @Bean
    public Queue queueB(){
        return new Queue(QUEUE_B,true);//队列持久
    }
    @Bean
    public Queue delayQueue(){
        Map<String, Object> params = new HashMap<>(16);
        // x-dead-letter-exchange 声明了队列里的死信转发到的DLX名称，
        params.put("x-dead-letter-exchange", EXCHANGE_A);
        // x-dead-letter-routing-key 声明了这些死信在转发时携带的 routing-key 名称。
        params.put("x-dead-letter-routing-key", ROUTINGKEY_A);
        return new Queue(DELAY_QUEUE, true, false, false, params);
    }
    @Bean
    public Binding binding(){
        return BindingBuilder.bind(queueA()).to(defaultExchange()).with(ROUTINGKEY_A);
    }
    @Bean
    public Binding bindingB(){
        return BindingBuilder.bind(queueB()).to(defaultExchange()).with(ROUTINGKEY_B);
    }
    @Bean
    public Binding delayBinding(){
        return  BindingBuilder.bind(delayQueue()).to(deadLetterExchange()).with(DELAY_ROUTING_KEY);
    }
}
