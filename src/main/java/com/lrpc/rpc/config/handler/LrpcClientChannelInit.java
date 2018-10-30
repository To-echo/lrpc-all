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
    private LrpClientHandler lrpClientHandler;

    public LrpcClientChannelInit() {
        lrpClientHandler = new LrpClientHandler();
    }

    @Override
    protected void initChannel(Channel ch) {
        //请求加密
        ch.pipeline().addLast(new RpcEncode(RpcRequest.class))
                .addLast(new RpcDecode(RpcResponse.class))
                .addLast(new LoggingHandler(LogLevel.INFO))
                .addLast(lrpClientHandler);
    }
    public synchronized void initHandler(LrpClientHandler lrpClientHandler){
        this.lrpClientHandler = lrpClientHandler;
    }
    public LrpClientHandler getLrpClientHandler() {
        return lrpClientHandler;
    }

    public void setLrpClientHandler(LrpClientHandler lrpClientHandler) {
        this.lrpClientHandler = lrpClientHandler;
    }
}
