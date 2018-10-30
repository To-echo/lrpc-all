package com.lrpc.rpc.vo;

import java.util.HashMap;

/**
 * @author tianp
 **/
public class RpcRequest {
    public RpcRequest() {
    }

    private Long id;
    /**
     * rpc name
     */
    private String className;
    /**
     * 方法名
     */
    private String methodName;
    /**
     * 参数
     */
    private HashMap<Class<?>, Object> arguments;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public HashMap<Class<?>, Object> getArguments() {
        return arguments;
    }

    public void setArguments(HashMap<Class<?>, Object> arguments) {
        this.arguments = arguments;
    }

    @Override
    public String toString() {
        return "RpcRequest{" +
                "id=" + id +
                ", className='" + className + '\'' +
                ", methodName='" + methodName + '\'' +
                ", arguments=" + arguments +
                '}';
    }
}
