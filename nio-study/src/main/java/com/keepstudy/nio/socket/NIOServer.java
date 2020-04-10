package com.keepstudy.nio.socket;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * @author: 莫升康
 * @e-mail: 1634414600@qq.com
 * @Date: 2020/4/9 10:11
 * @Description: 网络服务器端
 */
public class NIOServer {
    public static void main(String[] args) throws Exception{
        //1.得到一个网络服务器端，老大
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //2.得到一个 Selector 对象，间谍
        Selector selector = Selector.open();
        //3.绑定一个端口
        serverSocketChannel.bind(new InetSocketAddress(9999));
        //4.设置阻塞方式为非阻塞
        serverSocketChannel.configureBlocking(false);
        //5.把 ServerSocketChannel 对象注册到 Selector对象
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        //6.干活
        while (true) {
            //6.1监控客户端
            if (selector.select(2000) == 0) {
                System.out.println("目前没有客户端连接，我休息一会");
                continue;
            }

            //6.2得到SelectionKey,判断通道里的事件
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();

                //客户端连接事件
                if (key.isAcceptable()) {
                    System.out.println("OP_ACCEPT");
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector,SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                }

                //读取客户端数据事件
                if (key.isReadable()) {
                    System.out.println("OP_READ");
                    SocketChannel socketChannel = (SocketChannel)key.channel();
                    ByteBuffer byteBuffer = (ByteBuffer) key.attachment();
                    socketChannel.read(byteBuffer);
                    System.out.println("收到客户端发过来的数据："+new String(byteBuffer.array()));
                }

                //6.3手动从集合中移除当前key,防止重复处理
                iterator.remove();
            }
        }
    }
}
