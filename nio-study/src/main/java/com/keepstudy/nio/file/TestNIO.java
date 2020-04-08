package com.keepstudy.nio.file;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author: 莫升康
 * @e-mail: 1634414600@qq.com
 * @Date: 2020/4/8 08:38
 * @Description: NIO对文件的读写操作
 */
public class TestNIO {

    /**
     * 往本地文件中写数据
     */
    @Test
    public void writeFile() throws Exception {
        //1.创建输出流
        FileOutputStream fileOutputStream = new FileOutputStream("basic.txt");
        //2.从流中获取通道
        FileChannel channel = fileOutputStream.getChannel();
        //3.创建一个缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        //4.往缓冲区中存入数据
        String data = "hello,nio,你好";
        byteBuffer.put(data.getBytes());
        //5.翻转缓冲区
        byteBuffer.flip();
        //6.把缓冲区写到通道中
        channel.write(byteBuffer);
        //7.关闭资源
        fileOutputStream.close();
    }

    /**
     * 从本地文件中读取数据
     */
    @Test
    public void readFile() throws Exception {
        File file = new File("basic.txt");
        //1.创建输入流
        FileInputStream fileInputStream = new FileInputStream(file);
        //2.得到一个通道
        FileChannel channel = fileInputStream.getChannel();
        //3.准备一个缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate((int) file.length());
        //4.从通道中读取数据并存到缓冲区中
        channel.read(byteBuffer);
        System.out.println(new String(byteBuffer.array()));
        //5.关闭资源
        fileInputStream.close();
    }

    /**
     * 复制文件
     */
    @Test
    public void copyFile() throws Exception {
        //1.创建两个流，输入流和输出流
        File file = new File("basic.txt");
        FileInputStream fileInputStream = new FileInputStream(file);
        FileOutputStream fileOutputStream = new FileOutputStream("f:\\data\\basic.txt");

        //2.得到两个通道
        FileChannel inputStreamChannel = fileInputStream.getChannel();
        FileChannel outputStreamChannel = fileOutputStream.getChannel();

        //3.复制(适合大文件)
        //模式一：目标文件从数据源文件中读取
        outputStreamChannel.transferFrom(inputStreamChannel,0,inputStreamChannel.size());
        //outputStreamChannel.transferFrom(inputStreamChannel,0,file.length());
        //模式二：数据源文件写到目标文件中
        //inputStreamChannel.transferTo(0,inputStreamChannel.size(),outputStreamChannel);
        //inputStreamChannel.transferTo(0,file.length(),outputStreamChannel);

        //4.关闭资源
        fileInputStream.close();
        fileOutputStream.close();
    }
}
