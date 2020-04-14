package com.keepstudy.nio.chat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.logging.SimpleFormatter;

/**
 * @Author: moshengkang
 * @e-mial: 1634414600@qq.com
 * @Version: 1.0
 * @Description: 聊天程序服务端
 */
public class ChatServer {
    //监听通道，老大
    private ServerSocketChannel listenerChannel;
    //选择器对象，间谍
    private Selector selector;
    //服务器端口
    private static final int PORT=9999;

    /**
     * 初始化变量
     */
    public ChatServer() {
        try {
            //1.得到监听通道，老大
            listenerChannel = ServerSocketChannel.open();
            //2.得到选择器对象
            selector = Selector.open();
            //3.绑定端口
            listenerChannel.bind(new InetSocketAddress(PORT));
            //4.设置阻塞方式为非阻塞
            listenerChannel.configureBlocking(false);
            //5.选择器绑定到监听通道并监听accept事件
            listenerChannel.register(selector, SelectionKey.OP_ACCEPT);
            printInfo("chat server is ready");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 打印信息
     * @param msg
     */
    private void printInfo(String msg) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("["+simpleDateFormat.format(new Date())+"] ->"+msg);
    }

    /**
     * 6.干活
     */
    public void start() {
        try {
            //循环监控
            while (true) {
                if (selector.select(2000) == 0) {
                    System.out.println("server:没有客户端连接，我先休息一会");
                    continue;
                }
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    //客户端连接请求事件
                    if (key.isAcceptable()) {
                        SocketChannel channel = listenerChannel.accept();
                        channel.configureBlocking(false);
                        channel.register(selector,SelectionKey.OP_READ);
                        System.out.println(channel.getRemoteAddress().toString().substring(1)+"上线了...");
                    }
                    //读取数据事件
                    if (key.isReadable()) {
                        readMsg(key);
                    }
                    //一定要把当前key删除，防止重复处理
                    iterator.remove();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取客户端发来的消息并广播出去
     * @param key
     */
    private void readMsg(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        int count = channel.read(byteBuffer);
        if (count > 0){
            String msg = new String(byteBuffer.array());
            printInfo(msg);
            //发广播
            broadCast(channel,msg);
        }
    }

    /**
     * 发广播
     * @param except 发送消息的客户端
     * @param msg 消息内容
     */
    private void broadCast(SocketChannel except, String msg) throws IOException {
        System.out.println("服务器发送了广播...");
        for (SelectionKey key : selector.keys()) {
            Channel targetChannel = key.channel();
            if (targetChannel instanceof SocketChannel && targetChannel != except) {
                SocketChannel destChannel = (SocketChannel) targetChannel;
                ByteBuffer byteBuffer = ByteBuffer.wrap(msg.getBytes());
                destChannel.write(byteBuffer);
            }
        }
    }

    public static void main(String[] args) {
        new ChatServer().start();
    }
}
