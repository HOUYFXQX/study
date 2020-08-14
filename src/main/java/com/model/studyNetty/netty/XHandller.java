package com.model.studyNetty.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @ClassName XHandller
 * @Description TODO
 * @Author fyh
 * @Date 2020/6/3 16:11
 */
public class XHandller extends ChannelInboundHandlerAdapter {
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx)throws Exception{
         ctx.flush();
    };

    @Override
    public void channelRead(ChannelHandlerContext ctx,Object msg)throws Exception{
        //输出 bytebuf
        ByteBuf buf=(ByteBuf) msg;
        byte[] content=new byte[buf.readableBytes()];
        buf.readBytes(content);
        System.out.println(new String(content));
    };

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx,Throwable cause)throws Exception{
        cause.printStackTrace();
        ctx.close();
    };
}
