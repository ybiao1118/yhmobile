package com.yinhai.rabbitMq.controller;

import com.yinhai.rabbitMq.service.MessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


/** RabbitMQ消息测试类
 * @author yanbiao
 * @since 2020/4/10 14:13
 */
@RestController
@RequestMapping("/message")
@Api(tags = {"消息中间件"},description = "/message", produces = APPLICATION_JSON_VALUE)
public class MessageController {
    @Autowired
    private MessageService service;
    @ApiOperation(value = "发送及时消息")
    @PostMapping("/sendMsg")
    private void sendMsg(String msg){
        service.sendMsg(msg);
    }
    @ApiOperation(value = "发送延时消息")
    @PostMapping("/sendDelayMsg")
    private void sendDelayMsg(String msg, int delayTime){
        service.sendDelayMsg(msg,delayTime);
    }

}
