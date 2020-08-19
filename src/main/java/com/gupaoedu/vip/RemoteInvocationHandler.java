package com.gupaoedu.vip;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author : lipu
 * @since : 2020-08-18 23:03
 */
public class RemoteInvocationHandler implements InvocationHandler {

    private String host;
    private int port;

    public RemoteInvocationHandler(String host, int port) {
        this.host = host;
        this.port = port;
    }




    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //请求会进入这里
        System.out.println("coom in");
        //请求数据的封装
        RpcRequest rpcRequest = new RpcRequest();
        rpcRequest.setClassName(method.getDeclaringClass().getName());
        rpcRequest.setMethodName(method.getName());
        rpcRequest.setParameters(args);

        //远程通信
        RpcNetTransport netTransport = new RpcNetTransport(host,port);
        Object result = netTransport.send(rpcRequest);
        return result;
    }
}
