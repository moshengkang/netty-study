package com.keepstudy.bio;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author: 莫升康
 * @e-mail: 1634414600@qq.com
 * @Date: 2020/4/7 13:12
 * @Description: BIO客户端
 */
public class TCPClient {

    public static void main(String[] args) throws Exception{
        //1.创建Socket对象
        Socket socket = new Socket("127.0.0.1", 9999);
        //2.从连接中取出输出流并发送消息
        OutputStream outputStream = socket.getOutputStream();
        System.out.println("请输入:");
        Scanner scanner = new Scanner(System.in);
        String next = scanner.next();
        outputStream.write(next.getBytes());
        //3.从连接中取出输入流并接受回话
        InputStream inputStream = socket.getInputStream();//阻塞
        byte[] bytes = new byte[1024];
        inputStream.read(bytes);
        System.out.println("老板说:"+new String(bytes).trim());
        //4.关闭资源
        socket.close();
    }
}
