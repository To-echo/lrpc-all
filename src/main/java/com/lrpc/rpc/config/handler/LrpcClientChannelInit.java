package com.lrpc.rpc.config.handler;

import com.lrpc.rpc.vo.RpcRequest;
import com.lrpc.rpc.vo.RpcResponse;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @author tianp
 **/
public class LrpcClientChannelInit extends ChannelInitializer {
    private LrpcClientHandler lrpcClientHandler;

    public LrpcClientChannelInit() {
        lrpcClientHandler = new LrpcClientHandler();
    }

    @Override
    protected void initChannel(Channel ch) {
        //请求加密
        ch.pipeline().addLast(new RpcEncode(RpcRequest.class))
                .addLast(new RpcDecode(RpcResponse.class))
                .addLast(new LoggingHandler(LogLevel.INFO))
                .addLast(lrpcClientHandler);
    }

    public LrpcClientHandler getLrpClientHandler() {
        return lrpcClientHandler;
    }

    public void setLrpClientHandler(LrpcClientHandler lrpClientHandler) {
        this.lrpcClientHandler = lrpClientHandler;
    }
}
