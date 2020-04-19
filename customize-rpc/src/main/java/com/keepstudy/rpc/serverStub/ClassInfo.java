package com.keepstudy.rpc.serverStub;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: moshengkang
 * @e-mial: 1634414600@qq.com
 * @Version: 1.0
 * @Description: 封装类信息
 */
@Data
public class ClassInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 类名
     */
    private String className;

    /**
     * 方法名
     */
    private String methodName;

    /**
     * 参数类型
     */
    private Class<?>[] types;

    /**
     * 参数列表
     */
    private Object[] objects;

}
