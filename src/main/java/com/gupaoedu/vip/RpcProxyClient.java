package com.gupaoedu.vip;

import com.gupaoedu.vip.discovery.IServiceDiscovery;
import com.gupaoedu.vip.discovery.ServiceDiscoveryWithZk;

import java.lang.reflect.Proxy;

/**
 * 代理类
 * @author : lipu
 * @since : 2020-08-18 23:00
 */
public class RpcProxyClient {

    private IServiceDiscovery serviceDiscovery=new ServiceDiscoveryWithZk();

    public <T> T clientProxy(final Class<T> interfaceCls, String version){
       return (T)Proxy.newProxyInstance(interfaceCls.getClassLoader()
               ,new Class<?>[]{interfaceCls},new RemoteInvocationHandler(serviceDiscovery,version));
    }
}
