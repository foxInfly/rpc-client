package com.gupaoedu.vip;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        RpcProxyClient rpcProxyClient = new RpcProxyClient();

        IHelloService iHelloService = rpcProxyClient.clientProxy(IHelloService.class,"127.0.0.1",8080);
        String result = iHelloService.sayHello("pupu");

        System.out.println(result);

    }
}
