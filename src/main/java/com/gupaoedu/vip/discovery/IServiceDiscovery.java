package com.gupaoedu.vip.discovery;


/**
 * 服务发现
 *
 * @author lipu
 * @since  2020/9/20 21:58
 */
public interface IServiceDiscovery {


    /**根据服务名称返回服务地址
     * @param serviceName  服务名称
     * @author lipu
     * @since 2020/9/20 21:59
     */
    String  discovery(String serviceName);
}
