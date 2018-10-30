package com.lrpc.rpc.vo;

/**
 * 请求响应
 *
 * @author tianp
 **/
public class RpcResponse {
    public RpcResponse() {
    }

    private Long id;
    private Integer code;
    private Object result;
    private String failMsg;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public String getFailMsg() {
        return failMsg;
    }

    public void setFailMsg(String failMsg) {
        this.failMsg = failMsg;
    }

    @Override
    public String toString() {
        return "RpcResponse{" +
                "id=" + id +
                ", code=" + code +
                ", result=" + result +
                ", failMsg='" + failMsg + '\'' +
                '}';
    }
}
