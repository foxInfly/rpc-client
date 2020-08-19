package com.gupaoedu.vip;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        /*RpcProxyClient rpcProxyClient = new RpcProxyClient();

        IHelloService iHelloService = rpcProxyClient.clientProxy(IHelloService.class,"127.0.0.1",8080);
        String result = iHelloService.sayHello("pupu");

        System.out.println(result);*/

        ApplicationContext context = new AnnotationConfigApplicationContext(SprigConfig.class);
        RpcProxyClient rpcProxyClient = context.getBean(RpcProxyClient.class);

        IHelloService iHelloService = rpcProxyClient.clientProxy(IHelloService.class,"127.0.0.1",8080);
        String result = iHelloService.sayHello("pupu");

        System.out.println(result);

    }
}
