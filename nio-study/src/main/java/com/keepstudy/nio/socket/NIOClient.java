package com.keepstudy.nio.socket;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @author: 莫升康
 * @e-mail: 1634414600@qq.com
 * @Date: 2020/4/9 09:55
 * @Description: 网络客户端程序
 */
public class NIOClient {
    public static void main(String[] args) throws Exception{
        //1.得到一个网络客户端
        SocketChannel socketChannel = SocketChannel.open();

        //2.设置阻塞方式为非阻塞
        socketChannel.configureBlocking(false);

        //3.提供服务器端的IP地址和端口
        InetSocketAddress socketAddress = new InetSocketAddress("127.0.0.1", 9999);

        //4.连接服务器
        if (!socketChannel.connect(socketAddress)) {
            //连不上，继续连接
            while (!socketChannel.finishConnect()) {
                //nio作为非阻塞的优势
                System.out.println("可以做其他业务");
            }
        }

        //5.得到一个缓冲区并存入数据
        String msg ="hello,server";
        ByteBuffer byteBuffer = ByteBuffer.wrap(msg.getBytes());

        //6.发送数据
        socketChannel.write(byteBuffer);

        //客户端不能终止程序,否则服务器端出现异常
        System.in.read();
    }
}
