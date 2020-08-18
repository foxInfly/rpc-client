package com.gupaoedu.vip;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        RpcProxyClient rpcProxyClient = new RpcProxyClient();
        IHelloService iHelloService = rpcProxyClient.clientProxy(IHelloService.class,"127.0.0.1",8080);
        iHelloService.sayHello("pupu");
    }
}
