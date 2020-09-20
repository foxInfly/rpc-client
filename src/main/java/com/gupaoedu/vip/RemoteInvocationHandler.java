package com.gupaoedu.vip;

import com.gupaoedu.vip.discovery.IServiceDiscovery;
import org.springframework.util.StringUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author : lipu
 * @since : 2020-08-18 23:03
 */
public class RemoteInvocationHandler implements InvocationHandler {

//    private String host;
//    private int port;
    private IServiceDiscovery serviceDiscovery;
    private String version;


    public RemoteInvocationHandler(IServiceDiscovery serviceDiscovery, String version) {
//        this.host = host;
//        this.port = port;
        this.serviceDiscovery=serviceDiscovery;
        this.version=version;
    }




    /*@Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //请求会进入这里
        System.out.println("coom in");
        //请求数据的封装
        RpcRequest rpcRequest = new RpcRequest();
        rpcRequest.setClassName(method.getDeclaringClass().getName());
        rpcRequest.setMethodName(method.getName());
        rpcRequest.setParameters(args);
        rpcRequest.setVersion("v2.0");

        //远程通信
        RpcNetTransport netTransport = new RpcNetTransport(host,port);
        Object result = netTransport.send(rpcRequest);
        return result;
    }*/
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //请求数据的包装
        RpcRequest rpcRequest=new RpcRequest();
        rpcRequest.setClassName(method.getDeclaringClass().getName());
        rpcRequest.setMethodName(method.getName());
        rpcRequest.setParamTypes(method.getParameterTypes());
        rpcRequest.setParameters(args);
        rpcRequest.setVersion(version);
        String serviceName=rpcRequest.getClassName();
        if(!StringUtils.isEmpty(version)){
            serviceName=serviceName+"-"+version;
        }
        String serviceAddress=serviceDiscovery.discovery(serviceName);
        //远程通信
        RpcNetTransport netTransport=new RpcNetTransport(serviceAddress);
        Object result=netTransport.send(rpcRequest);

        return result;
    }
}
