package com.model.studyNetty.test;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;

/**
 * @ClassName HyfSocketClient
 * @Description TODO
 * @Author fyh
 * @Date 2020/6/12 16:18
 */
public class HyfSocketClient {
    
    public static void main(String[] args) throws Exception {
        Socket socket=new Socket("localhost",9999);
        OutputStream outputStream =socket.getOutputStream();
        //消息长度固定为220字节 ，包含有
        //1.目标用户id长度为10，10 000 000 000~19 999 999 999
        //2.消息内容字符串长度最多为70  一个汉按3个字节算，内容长度最大为210字节

        byte []request=new byte[220];
        byte[]userId="10000000000".getBytes();
        byte[]conten="hyf你好鸟蛤大家库存大仓库的拿手的摩擦圣诞节三大名成都市两节课时间逻辑卡斯柯垃圾".getBytes();
        System.arraycopy(userId,0,request,0,10);
        System.arraycopy(conten,0,request,10,conten.length);

        CountDownLatch countDownLatch=new CountDownLatch(10);
        for (int i = 0; i <10 ; i++) {
            new Thread(()->{
                try {
                    outputStream.write(request);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                countDownLatch.countDown();
            }).start();
        }
        countDownLatch.await();
        Thread.sleep(2000L);
        socket.close();
    } 
}
