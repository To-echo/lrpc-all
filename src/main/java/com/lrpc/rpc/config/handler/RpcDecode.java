package com.lrpc.rpc.config.handler;

import com.lrpc.rpc.utils.SerializationUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * 解码
 *
 * @author tianp
 **/
public class RpcDecode extends ByteToMessageDecoder {
    private Class<?> genericClass;

    public RpcDecode(Class<?> clazz) {
        this.genericClass = clazz;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx,
                          ByteBuf in, List<Object> out) throws Exception {
        int dataLength = in.readInt();
        //一个整数4个字节
        if (dataLength < 4) {
            return;
        }
        in.markReaderIndex();
        if (in.readableBytes() < dataLength) {
            in.resetReaderIndex();
            return;
        }
        byte[] data = new byte[dataLength];
        in.readBytes(data);
        Object obj = SerializationUtil.deserialize(data, genericClass);
        out.add(obj);
    }
}
