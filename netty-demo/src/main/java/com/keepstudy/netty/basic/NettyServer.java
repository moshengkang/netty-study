package com.keepstudy.netty.basic;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @Author: moshengkang
 * @e-mial: 1634414600@qq.com
 * @Version: 1.0
 * @Description: 服务器端
 */
public class NettyServer {
    public static void main(String[] args) throws Exception{
        //1.创建一个线程组：接受客户端连接
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        //2.创建一个线程组：处理网络操作
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        //3.创建服务器端启动助手来配置参数
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup,workerGroup)//4.设置两个线程组
            .channel(NioServerSocketChannel.class)//5.使用 NioServerSocketChannel 作为服务器端通道的实现
            .option(ChannelOption.SO_BACKLOG,128)//6.设置线程队列中的等待连接个数
            .childOption(ChannelOption.SO_KEEPALIVE,true)//7.保持活动连接状态
            .childHandler(new ChannelInitializer<SocketChannel>() {//8.创建一个通道初始化对象
                //9.往 Pipeline 链中添加自定义的Handler类
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    socketChannel.pipeline().addLast(new NettyServerHandler());
                }
            });

        System.out.println("...... server is ready ......");
        //10.绑定端口 bind() 方法是异步的，sync() 是同步的
        ChannelFuture channelFuture = bootstrap.bind(9999).sync();
        System.out.println("...... server is starting ......");

        //11.关闭通道，关闭线程组 closeFuture() 异步
        channelFuture.channel().closeFuture().sync();
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }
}
