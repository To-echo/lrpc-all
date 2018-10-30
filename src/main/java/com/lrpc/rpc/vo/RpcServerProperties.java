package com.lrpc.rpc.vo;

/**
 * @author tianp
 **/
public class RpcServerProperties {
    public RpcServerProperties() {
    }

    private String host;
    private Integer port;
    private String desc;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
