package com.study.rabbitmq.mq.springCloudStreamRabbitmq;

import com.study.example.model.MessageEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName StreamController
 * @Description TODO
 * @Author fyh
 * @Date 2020/8/7 15:37
 */
@RestController
public class StreamController {

    @Autowired
    private StreamClient streamClient;

    @GetMapping("/sendMessage")
    public void send(){
        //org.springframework.messaging.support.MessageBuilder;
        streamClient.output().send(MessageBuilder.withPayload(new MessageEntity()).build()); //构建消息并且发送
    }
}
