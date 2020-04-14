package com.keepstudy.nio.chat;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * @Author: moshengkang
 * @e-mial: 1634414600@qq.com
 * @Version: 1.0
 * @Description: 测试聊天程序
 */
public class TestChat {
    public static void main(String[] args) throws IOException {
        ChatClient chatClient = new ChatClient();

        new Thread(() -> {
            while (true) {
                try {
                    chatClient.receiveMsg();
                    try {
                        TimeUnit.SECONDS.sleep(2);} catch (InterruptedException e) {e.printStackTrace();}
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String msg = scanner.nextLine();
            chatClient.sendMsg(msg);
        }
    }
}
