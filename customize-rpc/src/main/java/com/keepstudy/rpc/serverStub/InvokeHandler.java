package com.keepstudy.rpc.serverStub;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.reflections.Reflections;

import java.lang.reflect.Method;
import java.util.Set;

/**
 * @Author: moshengkang
 * @e-mial: 1634414600@qq.com
 * @Version: 1.0
 * @Description: 服务端业务处理类
 */
public class InvokeHandler extends ChannelInboundHandlerAdapter {

    /**
     * 主要使用反射技术
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //得到远程调用信息
        ClassInfo classInfo = (ClassInfo)msg;
        //获取远程调用类的实现类
        Object clazz = Class.forName(getImplClassName(classInfo)).newInstance();
        //获取远程需要调用的方法
        Method method = clazz.getClass().getMethod(classInfo.getMethodName(), classInfo.getTypes());
        //通过反射调用实现类的方法
        Object result = method.invoke(clazz, classInfo.getObjects());
        //将结果返回给客户端
        ctx.writeAndFlush(result);
    }

    /**
     * 获取实现类
     * @param classInfo
     * @return
     */
    private String getImplClassName(ClassInfo classInfo) throws Exception{
        //服务方所在接口和实现类所在包路径
        String interfacePath = "com.keepstudy.rpc.server";
        int lastIndexOf = classInfo.getClassName().lastIndexOf(".");
        String interfaceName = classInfo.getClassName().substring(lastIndexOf);
        Class superClass = Class.forName(interfacePath+interfaceName);
        Reflections reflections = new Reflections(interfacePath);
        //得到接口的所有实现类
        Set<Class> implClassSet = reflections.getSubTypesOf(superClass);
        if (implClassSet.size() == 0) {
            System.out.println("没有找到"+superClass.getName()+"实现类");
            return null;
        } else if (implClassSet.size() > 1) {
            System.out.println("找到"+superClass.getName()+"多个实现类，没有明确指明使用哪个实现类");
            return null;
        } else {
            //把集合转为数组
            Class[] classes = implClassSet.toArray(new Class[0]);
            return classes[0].getName();
        }
    }
}
