package com.lrpc.rpc.config.handler;

import com.lrpc.rpc.vo.RpcRequest;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @author tianp
 **/
public class LrpcChannelInit extends ChannelInitializer {
    private RpcEncode rpcEncode;
    private RpcHandler rpcHandler;
    private Integer port;

    public LrpcChannelInit(RpcEncode rpcEncode, RpcHandler rpcHandler, Integer port) {
        this.rpcEncode = rpcEncode;
        this.rpcHandler = rpcHandler;
        this.port = port;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        //先对信息解码，处理后再编码发送，bytetomessage不能共享
        ch.pipeline().addLast(new RpcDecode(RpcRequest.class))
                .addLast(rpcEncode)
                .addLast(new LoggingHandler(LogLevel.INFO))
                .addLast(rpcHandler);

    }
}
