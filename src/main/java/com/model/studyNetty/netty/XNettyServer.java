package com.model.studyNetty.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @ClassName XNettyServer
 * @Description TODO
 * @Author fyh
 * @Date 2020/6/3 15:18
 */
public class XNettyServer {
    public static void main(String[] args) throws Exception{
        //1线程定义
        //accept处理连接的线程池
        EventLoopGroup acceptGroup=new NioEventLoopGroup();
        //read io  处理数据的线程池
        EventLoopGroup readGroup=new NioEventLoopGroup();

        try {
            ServerBootstrap b=new ServerBootstrap();
            b.group(acceptGroup,readGroup);
            //选择TCP协议，nio 的实现方式
            b.channel(NioServerSocketChannel.class);
            b.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel c) throws Exception {
                        //职责链定义（请求收到后怎么处理）
                        ChannelPipeline pipeline = c.pipeline();
                    pipeline.addLast(new XDecode());
                    pipeline.addLast(new XHandller());
                    //TODO 打印出内容handdler
                }
            });
           // 绑定端口
            System.out.println("启动成功，端口号9999");
            b.bind(9999).sync().channel().closeFuture().sync();
        }finally {
            acceptGroup.shutdownGracefully();
            readGroup.shutdownGracefully();
        }
    }
}
