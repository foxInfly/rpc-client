package com.gupaoedu.vip;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : lipu
 * @since : 2020-08-19 23:32
 */
@Configuration
public class SprigConfig {


    @Bean(name = "rpcProxyClient")
    public RpcProxyClient proxyClient(){
        return new RpcProxyClient();
    }
}


