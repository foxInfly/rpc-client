package com.gupaoedu.vip;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws InterruptedException {
        /*RpcProxyClient rpcProxyClient = new RpcProxyClient();

        IHelloService iHelloService = rpcProxyClient.clientProxy(IHelloService.class,"127.0.0.1",8080);
        String result = iHelloService.sayHello("pupu");

        System.out.println(result);*/

        ApplicationContext context = new AnnotationConfigApplicationContext(SprigConfig.class);
        RpcProxyClient rpcProxyClient = context.getBean(RpcProxyClient.class);

//        IHelloService iHelloService = rpcProxyClient.clientProxy(IHelloService.class,"127.0.0.1",8080);
        IHelloService iHelloService = rpcProxyClient.clientProxy(IHelloService.class,"v2.0");
        for(int i=0;i<100;i++) {
            Thread.sleep(2000);
            System.out.println(iHelloService.sayHello(1.0));
        }

    }
}
