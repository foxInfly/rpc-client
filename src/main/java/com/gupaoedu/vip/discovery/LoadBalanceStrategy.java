package com.gupaoedu.vip.discovery;

import java.util.List;

/**
 * 负载均衡策略
 *
 * @author lipu
 * @since  2020/9/20 22:11
 */
public interface LoadBalanceStrategy {


    /**选择主机
     * @author lipu
     * @since 2020/9/20 22:12
     */
    String selectHost(List<String> repos);

}
