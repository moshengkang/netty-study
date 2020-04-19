package com.keepstudy.rpc.client;

import com.keepstudy.rpc.clientStub.NettyRPCProxy;

/**
 * @Author: moshengkang
 * @e-mial: 1634414600@qq.com
 * @Version: 1.0
 * @Description: 客户端测试
 */
public class TestNettyRPC {
    public static void main(String[] args) {
        //第一次远程调用
       HelloNetty helloNetty = (HelloNetty) NettyRPCProxy.create(HelloNetty.class);
        System.out.println(helloNetty.sayHello());

        //第二次调用
        HelloRPC helloRPC = (HelloRPC) NettyRPCProxy.create(HelloRPC.class);
        System.out.println(helloRPC.hello("RPC"));
    }
}
