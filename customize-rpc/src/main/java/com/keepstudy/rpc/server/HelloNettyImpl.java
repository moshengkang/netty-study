package com.keepstudy.rpc.server;

/**
 * @Author: moshengkang
 * @e-mial: 1634414600@qq.com
 * @Version: 1.0
 * @Description: java类作用描述
 */
public class HelloNettyImpl implements HelloNetty{
    @Override
    public String sayHello() {
        return "hello,netty";
    }
}
