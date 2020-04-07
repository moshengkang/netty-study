package com.keepstudy.bio;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author: 莫升康
 * @e-mail: 1634414600@qq.com
 * @Date: 2020/4/7 13:15
 * @Description: BIO服务端
 */
public class TCPServer {
    public static void main(String[] args) throws Exception{

        //1.创建ServerSocket对象
        ServerSocket serverSocket = new ServerSocket(9999);

        while (true) {
            //2.监听客户端
            Socket socket = serverSocket.accept();//阻塞
            //3.从连接中取出输入流来接收消息
            InputStream inputStream = socket.getInputStream();//阻塞
            byte[] bytes = new byte[10];
            inputStream.read(bytes);
            String clientIP = socket.getInetAddress().getHostAddress();
            System.out.println(clientIP+"说:"+new String(bytes).trim());
            //4.从连接中取出输出流并回话
            OutputStream outputStream = socket.getOutputStream();
            outputStream.write("没钱".getBytes());
            //5.关闭资源
            socket.close();
        }
    }
}
