package com.model.studyNetty.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @ClassName XDecode
 * @Description TODO
 * @Author fyh
 * @Date 2020/6/12 16:37
 */
public class XDecode extends ByteToMessageDecoder {
    static final int PACKET_SIZE=220;
    //用来临时保留没有处理过的请求报文
    ByteBuf tempMsg= Unpooled.buffer();
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        System.out.println("收到一次数据包，长度是："+byteBuf.readableBytes());
        //byteBuf 请求的数据
        // list  将粘在一起的报文拆分后的结果保留起来

        //1 合并报文
        ByteBuf message=null;
        int tmpMsgSize=tempMsg.readableBytes();
        //如果暂存有上次余下的请求报文则合并
        if(tmpMsgSize>0){
            message=Unpooled.buffer();
            message.writeBytes(tempMsg);
            message.writeBytes(byteBuf);
            System.out.println("合并：上一数据包余下的长度为:"+tmpMsgSize+",合并后的长度为："+message.readableBytes());
        }else {
            message=byteBuf;
        }
        int size=message.readableBytes();
        int counter=size/PACKET_SIZE;
        for (int i = 0; i <counter ; i++) {
            byte [] request=new byte[PACKET_SIZE];
            message.writeBytes(request);
            list.add(Unpooled.copiedBuffer(request));
        }
        size=message.readableBytes();
        if(size!=0){
         System.out.println("多余的数据长度："+size);
         tempMsg.clear();
         tempMsg.writeBytes(message.readBytes(size));
        }


    }
}
