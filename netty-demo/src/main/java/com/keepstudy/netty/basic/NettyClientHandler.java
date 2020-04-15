package com.keepstudy.netty.basic;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * @Author: moshengkang
 * @e-mial: 1634414600@qq.com
 * @Version: 1.0
 * @Description: 客户端业务处理类
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    /**
     * 通道就绪事件
     * @param ctx
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        System.out.println("client:"+ctx);
        ctx.writeAndFlush(Unpooled.copiedBuffer("老板，还钱吧", CharsetUtil.UTF_8));
    }

    /**
     * 读取数据事件
     * @param ctx
     * @param msg
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf byteBuf = (ByteBuf)msg;
        System.out.println("服务器端发来的消息:"+byteBuf.toString(CharsetUtil.UTF_8));
    }
}
