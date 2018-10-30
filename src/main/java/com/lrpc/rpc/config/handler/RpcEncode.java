package com.lrpc.rpc.config.handler;

import com.lrpc.rpc.utils.SerializationUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 响应编码
 *
 * @author tianp
 **/
@ChannelHandler.Sharable
public class RpcEncode extends MessageToByteEncoder {
    private Class<?> genericClass;

    public RpcEncode(Class<?> clazz) {
        this.genericClass = clazz;
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext,
                          Object o, ByteBuf byteBuf) throws Exception {
        if (genericClass.isInstance(o)) {
            byte[] data = SerializationUtil.serialize(o);
            byteBuf.writeInt(data.length);
            byteBuf.writeBytes(data);
        }
    }
}
