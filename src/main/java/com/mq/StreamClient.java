package com.mq;

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
    //消息接受发送接口
    @Input("testMessage")
    SubscribableChannel input();  //用于接受消息

    @Output("testMessage")
    MessageChannel output();  //用于发送消息



}
