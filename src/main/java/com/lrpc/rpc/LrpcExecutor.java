package com.lrpc.rpc;

import com.lrpc.rpc.vo.RpcRequest;
import com.lrpc.rpc.vo.RpcResponse;

/**
 * @author tianp
 **/
public interface LrpcExecutor {
    RpcResponse execute(RpcRequest req);
}
