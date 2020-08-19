package com.gupaoedu.vip;

import java.lang.reflect.Proxy;

/**
 * 代理类
 * @author : lipu
 * @since : 2020-08-18 23:00
 */
public class RpcProxyClient {
    public <T> T clientProxy(final Class<T> interfaceCls,final String host,final int port){
       return (T)Proxy.newProxyInstance(interfaceCls.getClassLoader()
               ,new Class<?>[]{interfaceCls},new RemoteInvocationHandler(host, port));
    }
}
