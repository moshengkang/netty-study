package com.keepstudy.netty.chat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.EventExecutorGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: moshengkang
 * @e-mial: 1634414600@qq.com
 * @Version: 1.0
 * @Description: 自定义的处理器
 */
public class CharHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    //用来保存所有的客户端连接
    private static ChannelGroup clients = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd hh:MM");

    /**
     * 当Channel中有新的事件会自动调用
     * @param channelHandlerContext
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame msg) throws Exception {
        //当接收到数据后会自动调用
        //获取客户端发送过来的文本消息
        String text = msg.text();
        System.out.println("接收的消息数据为："+text);

        for (Channel client : clients) {
            //将消息发送到所有的客户端
            client.writeAndFlush(new TextWebSocketFrame(simpleDateFormat.format(new Date())+":"+text));
        }
    }

    /**
     * 当有新的客户端连接服务器之后，会自动调用这个方法
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        //将新的通道加入到clients
        clients.add(ctx.channel());
    }
}
