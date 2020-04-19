package com.keepstudy.rpc.server;

/**
 * @Author: moshengkang
 * @e-mial: 1634414600@qq.com
 * @Version: 1.0
 * @Description: java类作用描述
 */
public class HelloRPCImpl implements HelloRPC{
    @Override
    public String hello(String name) {
        return "hello"+name;
    }
}
