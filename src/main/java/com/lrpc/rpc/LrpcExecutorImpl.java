package com.lrpc.rpc;

import com.lrpc.rpc.config.handler.LrpClientHandler;
import com.lrpc.rpc.config.handler.LrpcClientChannelInit;
import com.lrpc.rpc.vo.RpcRequest;
import com.lrpc.rpc.vo.RpcResponse;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * @author tianp
 **/
public class LrpcExecutorImpl implements LrpcExecutor {
    private String host;
    private Integer port;

    public LrpcExecutorImpl(String host, Integer port) {
        this.host = host;
        this.port = port;
    }


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

    @Override
    public RpcResponse execute(RpcRequest rpcRequest) {
        LrpcClientChannelInit lrpcClientChannelInit = new LrpcClientChannelInit();
        Bootstrap b = new Bootstrap();
        EventLoopGroup group = null;
        ChannelFuture future = null;
        try {
            group = new NioEventLoopGroup();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(lrpcClientChannelInit)
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000)
                    .option(ChannelOption.SO_KEEPALIVE, true);
            future = b.connect(new InetSocketAddress(host, port)).sync();
            //TODO 连接好了直接发送消息,同步则阻塞等待通知
            future.channel().writeAndFlush(rpcRequest).sync();
            Object lock = lrpcClientChannelInit.getLrpClientHandler().getLock();
            synchronized (lock) {
                lock.wait();
            }
            RpcResponse rpcResponse = lrpcClientChannelInit.getLrpClientHandler().getRpcResponse();
            if (null != rpcResponse) {
                future.channel().closeFuture().sync();
            }
            return rpcResponse;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lrpcClientChannelInit.getLrpClientHandler().setRpcResponse(null);
            if (null != group) {
                group.shutdownGracefully();
            }
        }
        return null;
    }
}
