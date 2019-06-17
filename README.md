# lrpc-all #
基于netty和tcp的rpc调用框架

## 使用到技术 ##

SpringBoot 1.5.2、protosbuff 1.5.2、Netty4.0

## 用例 ##
    

1. clone 源码，build jar到本地仓库

2. 源码中带有spring-boot-web 1.5.2的jar包，因此可直接使用SpringBoot

3. 发布一个服务，在类上用@RpcService 标识，value值，为rpc方法的唯一标识，不能重复！

```
 @RpcService("HelloService")

    public class HelloServiceImpl implements HelloService {
        @Override
        public String say(String msg) {
            return "Hello Word!" + msg;
        }
    } 
```
   
4. 将rpc服务需要绑定的端口号写入spring的enviroment中，如：lrpc.server=8888

5. client端只需要配置一个执行的LrpcExecutor就ok，里面包含请求服务的host和port,如下：

```
lrpc.hello.host=127.0.0.1
lrpc.hello.port=8888
lrpc.hello.desc=hello rpc调用
```

6. 配置Executor

```
@Configuration
@Component
public class RpcConfiguration {

    @Bean("rpc.hello")
    @ConfigurationProperties(prefix = "lrpc.hello")
    public RpcServerProperties rpcClientCallProperties() {
        return new RpcServerProperties();
    }

    @Bean("helloRpcExecutor")
    LrpcExecutor lrpcExecutor(@Qualifier(value = "rpc.hello") RpcServerProperties rpcServerProperties) {
        return invoke(rpcServerProperties);
    }

    private LrpcExecutor invoke(RpcServerProperties config) {
        return new LrpcExecutorImpl(config.getHost(), config.getPort());
    }
}

```

7. 调用，RpcRequest请求类中的className为@RpcService中的value，methodName为@RpcService标识的类中的方法名


```
@Autowired
    @Qualifier(value = "helloRpcExecutor")
    private LrpcExecutor helloRpcExecutor;

    @GetMapping("/say")
    public String invoke(String msg) {
        RpcRequest rpcRequest = new RpcRequest();
        rpcRequest.setClassName("HelloService");
        rpcRequest.setMethodName("say");
        rpcRequest.setId(111L);
        HashMap<Class<?>, Object> arguments = new HashMap<>(8);
        arguments.put(String.class, "good");
        rpcRequest.setArguments(arguments);
        RpcResponse execute = helloRpcExecutor.execute(rpcRequest);
        System.out.println(execute.toString());
        return execute.toString();
    }
	.....
```
最后，以上为个人练手demo，未来有时间会把未完善的地方慢慢完善，最终目标是做成像Dubbo那样的pj，如有好的意见或疑惑欢迎各位大佬指点（morty630@foxmail.com）