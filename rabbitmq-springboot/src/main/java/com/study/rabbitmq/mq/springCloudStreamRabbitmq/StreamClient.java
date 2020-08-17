package com.study.rabbitmq.mq.springCloudStreamRabbitmq;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/**
 * @ClassName StreamClient
 * @Description TODO
 * @Author fyh
 * @Date 2020/8/7 15:34
 */
public interface StreamClient {
    @Output("testMessage")
    MessageChannel output();  //用于发送消息



}
