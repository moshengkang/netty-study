package com.keepstudy.nio.chat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.security.Permission;

/**
 * @Author: moshengkang
 * @e-mial: 1634414600@qq.com
 * @Version: 1.0
 * @Description: 聊天程序客户端
 */
public class ChatClient {
    //服务器地址
    private final String HOST = "127.0.0.1";
    //服务器端口
    private final int PORT = 9999;
    //网络通道
    private SocketChannel socketChannel;
    //聊天用户名
    private String userName;

    public ChatClient() throws IOException {
        //1.得到一个网络通道
        socketChannel = SocketChannel.open();
        //2.设置为非阻塞方式
        socketChannel.configureBlocking(false);
        //3.提供服务器的地址和端口号
        InetSocketAddress inetSocketAddress = new InetSocketAddress(HOST, PORT);
        //4.连接服务器
        if (!socketChannel.connect(inetSocketAddress)) {
            while (!socketChannel.finishConnect()) {
                System.out.println("Client:连接服务器的同时，我还可以做点其他的事");
            }
        }
        //5.得到客户端IP地址和端口信息，作为聊天用户名使用
        userName = socketChannel.getLocalAddress().toString().substring(1);
        System.out.println("-----------Client("+userName+")is ready-----------");
    }

    /**
     * 向服务器发送数据
     * @param msg
     * @throws IOException
     */
    public void sendMsg(String msg) throws IOException {
        if (msg.equalsIgnoreCase("bye")) {
            socketChannel.close();
            return;
        }
        msg = userName +"说:"+msg;
        ByteBuffer byteBuffer = ByteBuffer.wrap(msg.getBytes());
        socketChannel.write(byteBuffer);
    }

    /**
     * 接受服务器返回的数据
     * @throws IOException
     */
    public void receiveMsg() throws IOException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        int size = socketChannel.read(byteBuffer);
        if (size > 0) {
            String msg = new String(byteBuffer.array());
            System.out.println(msg.trim());
        }
    }
}
