package com.mq;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

/**
 * @ClassName StreamReceiver
 * @Description TODO
 * @Author fyh
 * @Date 2020/8/7 15:36
 */
@Component
@EnableBinding(StreamClient.class)
public class StreamReceiver {

    @StreamListener("testMessage2")  //监听testMessage这个消息队列, StreamClient类中必须定义相应的Input。
    public void receiver(Object message){
        System.out.println("接收到消息："+message);
    }
}
