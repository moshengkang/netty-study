package com.keepstudy.netty.chat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @Author: moshengkang
 * @e-mial: 1634414600@qq.com
 * @Version: 1.0
 * @Description: 聊天客户端业务处理
 */
public class ChatClientHandler extends SimpleChannelInboundHandler<String> {

    /**
     * 读取数据
     * @param channelHandlerContext
     * @param s
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) {
        System.out.println(s.trim());
    }
}
