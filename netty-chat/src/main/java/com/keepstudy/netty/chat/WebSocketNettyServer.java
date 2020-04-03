package com.keepstudy.netty.chat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @Author: moshengkang
 * @e-mial: 1634414600@qq.com
 * @Version: 1.0
 * @Description: netty服务器(主从多线程模型)
 */
public class WebSocketNettyServer {
    public static void main(String[] args) {
        //创建两个线程池
        NioEventLoopGroup mainGrp = new NioEventLoopGroup();//主线程池
        NioEventLoopGroup subGrp = new NioEventLoopGroup();//从线程池

        try {
            //创建netty服务器启动对象
            ServerBootstrap serverBootstrap = new ServerBootstrap();

            //初始化服务器启动对象
            serverBootstrap
                    //指定上面两个创建的线程池
                    .group(mainGrp,subGrp)
                    //指定netty通道类型
                    .channel(NioServerSocketChannel.class)
                    //指定通道初始化器用来加载Channel收到事件后，如何进行业务处理
                    .childHandler(new WebSocketChannelInitializer());

            //绑定服务器端口，已同步的方式启动服务器
            ChannelFuture future = serverBootstrap.bind(9090).sync();
            //等待服务器关闭
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //优雅关闭服务器
            mainGrp.shutdownGracefully();
            subGrp.shutdownGracefully();
        }

    }
}

