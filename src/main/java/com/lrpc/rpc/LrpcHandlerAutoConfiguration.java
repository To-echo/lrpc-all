package com.lrpc.rpc;

import com.lrpc.rpc.config.annotation.RpcService;
import com.lrpc.rpc.config.handler.*;
import com.lrpc.rpc.vo.RpcRequest;
import com.lrpc.rpc.vo.RpcResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

/**
 * @author tianp
 **/
@Configurable
@Component
@EnableAutoConfiguration
public class LrpcHandlerAutoConfiguration implements ApplicationContextAware {
    private ApplicationContext context;
    @Value("${lrpc.server}")
    public String port;

    @Bean
    public RpcHandler rpcHandler() {
        Map<String, Object> rpcBeans = context.getBeansWithAnnotation(RpcService.class);
        Set<String> beanNameSet = rpcBeans.keySet();
        for (String beanName : beanNameSet) {
            Object obj = rpcBeans.get(beanName);
            //rpcService注解会 把value的值传递给component
            RpcService annotation = obj.getClass().getDeclaredAnnotation(RpcService.class);
            //默认bean name
            if (StringUtils.isBlank(annotation.value()) || annotation.value().equals(beanName)) {
                continue;
            }
            rpcBeans.put(annotation.value(), rpcBeans.get(beanName));
            //去掉重复
            rpcBeans.remove(beanName);
        }
        return new RpcHandler(rpcBeans);
    }

    //-----------------------------server
//    @Bean("serverRequest")
//    public RpcDecode serverRequest() {
//        return new RpcDecode(RpcRequest.class);
//    }

    @Bean("serverResponse")
    public RpcEncode serverResponse() {
        return new RpcEncode(RpcResponse.class);
    }

    @Bean
    @ConditionalOnBean(value = {RpcHandler.class})
    public LrpcChannelInit lrpcChannelInit(RpcHandler rpcHandler,
                                           @Qualifier("serverResponse") RpcEncode rpcEncode) {
        //响应加密
        return new LrpcChannelInit( rpcEncode, rpcHandler, Integer.parseInt(port));
    }
    //-------------------------client
//    @Bean("clientResponse")
//    public RpcEncode clientResponse() {
//        return new RpcEncode(RpcRequest.class);
//    }
//    @Bean
//    public LrpcClientChannelInit lrpcClientChannelInit(@Qualifier("clientResponse") RpcEncode rpcEncode){
//        return new LrpcClientChannelInit(rpcEncode);
//    }
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }
}
