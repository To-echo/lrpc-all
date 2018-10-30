package com.lrpc.rpc.config.handler;

import com.lrpc.rpc.vo.RpcRequest;
import com.lrpc.rpc.vo.RpcResponse;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.sf.cglib.reflect.FastClass;
import net.sf.cglib.reflect.FastMethod;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * @author tianp
 **/
@ChannelHandler.Sharable
public class RpcHandler extends SimpleChannelInboundHandler<RpcRequest> {
    private static final Logger logger = Logger.getLogger(RpcHandler.class.getName());
    private Map<String, Object> rpcBeans;

    public RpcHandler(Map<String, Object> rpcBeans) {
        this.rpcBeans = rpcBeans;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcRequest msg) throws Exception {
        RpcResponse rpcResponse = handle(msg);
        ctx.channel().writeAndFlush(rpcResponse).addListener(ChannelFutureListener.CLOSE);
    }

    private RpcResponse handle(RpcRequest msg) throws InvocationTargetException {
        RpcResponse rpcResponse = new RpcResponse();
        Object obj = rpcBeans.get(msg.getClassName());
        //TODO 暂时这样吧
        if (Objects.isNull(obj)){
            System.out.println("未找到service");
            rpcResponse.setResult(null);
            rpcResponse.setCode(404);
            logger.warning("请求的service未找到,msg:"+msg.toString());
            return rpcResponse;
        }
        rpcResponse.setId(msg.getId());
        //解析请求，执行相应的rpc方法
        Class<?> clazz = obj.getClass();
        String methodName = msg.getMethodName();
        HashMap<Class<?>, Object> arguments = msg.getArguments();
        FastClass fastClass = FastClass.create(clazz);
        FastMethod method = fastClass.getMethod(methodName,
                arguments.keySet().toArray(new Class[arguments.size()]));
        Object result = method.invoke(obj, arguments.values().toArray());
        rpcResponse.setResult(result);
        rpcResponse.setCode(200);
        return rpcResponse;
    }
}
