package com.study.rabbitmq.mq.springCloudStreamRabbitmq;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.tools.json.JSONUtil;
import com.study.example.model.MessageEntity;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @ClassName StreamReceiver
 * @Description TODO
 * @Author fyh
 * @Date 2020/8/7 15:36
 */
@Component
@EnableBinding(StreamClient.class)
public class StreamReceiver {

    @StreamListener("testMessage")  //监听testMessage这个消息队列, StreamClient类中必须定义相应的Input。
    public void receiver(Message<MessageEntity>message){
        if(Objects.nonNull(message)){
            MessageEntity obj=message.getPayload();
            System.out.println("接收到消息："+ obj.toString());
        }
    }
}
