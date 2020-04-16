package com.keepstudy.netty.chat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: moshengkang
 * @e-mial: 1634414600@qq.com
 * @Version: 1.0
 * @Description: 聊天服务端业务处理
 */
public class ChatServerHandler extends SimpleChannelInboundHandler<String> {

    public static List<Channel> channels = new ArrayList<>();

    /**
     * 通道就绪
     * @param ctx 通道上下文
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        Channel inChannel = ctx.channel();
        channels.add(inChannel);
        System.out.println("[Server]:"+inChannel.remoteAddress().toString().substring(1)+"上线了");
    }

    /**
     * 通道未就绪
     * @param ctx 通道上下文
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        Channel inChannel = ctx.channel();
        channels.remove(inChannel);
        System.out.println("[Server]:"+inChannel.remoteAddress().toString().substring(1)+"离线了");
    }

    /**
     * 读取数据
     * @param ctx 通道上下文
     * @param s 客户端发来的数据
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String s) {
        Channel inChannel = ctx.channel();
        for (Channel channel : channels) {
            //排除当前通道
            if (channel != inChannel) {
                channel.writeAndFlush("["+inChannel.remoteAddress().toString().substring(1)+"]说"+s+"\n");
            }
        }
    }
}
