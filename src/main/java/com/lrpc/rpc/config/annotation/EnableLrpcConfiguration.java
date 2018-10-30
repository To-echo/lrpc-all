package com.lrpc.rpc.config.annotation;

import com.lrpc.rpc.LrpcHandlerAutoConfiguration;
import com.lrpc.rpc.LrpcServerImpl;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({LrpcHandlerAutoConfiguration.class,LrpcServerImpl.class})
public @interface EnableLrpcConfiguration {
}
