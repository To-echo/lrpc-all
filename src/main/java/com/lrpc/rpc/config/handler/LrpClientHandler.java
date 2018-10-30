package com.lrpc.rpc.config.handler;

import com.lrpc.rpc.vo.RpcResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author tianp
 **/
public class LrpClientHandler extends SimpleChannelInboundHandler<RpcResponse> {
    private final Object lock = new Object();
    private volatile RpcResponse rpcResponse = null;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcResponse msg) throws Exception {
        rpcResponse = msg;
        synchronized (lock) {
            lock.notifyAll();
        }
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    public Object getLock() {
        return lock;
    }

    public RpcResponse getRpcResponse() {
        return rpcResponse;
    }

    public void setRpcResponse(RpcResponse rpcResponse) {
        this.rpcResponse = rpcResponse;
    }

}
