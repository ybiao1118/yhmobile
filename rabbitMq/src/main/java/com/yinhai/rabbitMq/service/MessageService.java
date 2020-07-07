package com.yinhai.rabbitMq.service;

/**
 * @author yanbiao
 * @since 2020/4/10 13:56
 */
public interface MessageService {
    /**
     * 发送及时消息
     * @param msg 消息
     * @return
     */
    void sendMsg(String msg);

    /**
     * 发送延时消息
     * @param msg 消息
     * @param delayTime 延时时间 单位秒
     * @return
     */
    void sendDelayMsg(String msg, int delayTime);

}
