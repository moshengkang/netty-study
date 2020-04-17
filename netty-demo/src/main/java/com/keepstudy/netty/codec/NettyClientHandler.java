package com.keepstudy.netty.codec;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @Author: moshengkang
 * @e-mial: 1634414600@qq.com
 * @Version: 1.0
 * @Description: 客户端处理类
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    /**
     * 向服务端发送一个pojo
     * @param ctx
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx){
        BookMessage.Book book = BookMessage.Book.newBuilder().setId(1).setName("Java书籍").build();
        ctx.writeAndFlush(book);
    }
}
