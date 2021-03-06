package com.gupaoedu.vip.discovery;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.ArrayList;
import java.util.List;


/**
 * 通过zookeeper根据服务名称发现服务
 *
 * @author lipu
 * @since  2020/9/20 21:59
 */
public class ServiceDiscoveryWithZk implements IServiceDiscovery{

    CuratorFramework curatorFramework =null;

    List<String> serviceRepos=new ArrayList<>(); //服务地址的本地缓存,

    {
        //初始化zookeeper的连接， 会话超时时间是5s，衰减重试
        curatorFramework = CuratorFrameworkFactory.builder().
                connectString(ZkConfig.CONNECTION_STR).sessionTimeoutMs(5000).
                retryPolicy(new ExponentialBackoffRetry(1000, 3)).
                namespace("registry")
                .build();
        curatorFramework.start();
    }

    /**
     * 服务的查找,根据服务名查找对应地址，可能多个
     * 设置监听
     * 负载均衡，，只要一个
     * @param serviceName 服务名称
     * @return String
     */

    @Override
    public String discovery(String serviceName) {
        //完成了服务地址的查找(服务地址被删除)
        String path="/"+serviceName; //registry/com.gupaoedu.demo.HelloService
        if(serviceRepos.isEmpty()) {
            try {
                serviceRepos = curatorFramework.getChildren().forPath(path);
                System.out.println(path+"下的服务有：");
                for (int i = 0; i < serviceRepos.size(); i++) {
                    System.out.println("   "+serviceRepos.get(i));
                }

                registryWatch(path);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //针对已有的地址做负载均衡
        LoadBalanceStrategy loadBalanceStrategy=new RandomLoadBalance();
        return loadBalanceStrategy.selectHost(serviceRepos);
    }

    /**注册监听，子节点变化，刷新本地缓存
     * @author lipu
     * @since 2020/9/20 22:08
     */
    private void registryWatch(final String path) throws Exception {
        PathChildrenCache nodeCache=new PathChildrenCache(curatorFramework,path,true);
        PathChildrenCacheListener nodeCacheListener= (curatorFramework1, pathChildrenCacheEvent) -> {
            System.out.println("客户端收到节点变更的事件");
            serviceRepos=curatorFramework1.getChildren().forPath(path);// 再次更新本地的缓存地址
            System.out.println(path+"发生变化，当前的服务有：");
            for (int i = 0; i < serviceRepos.size(); i++) {
                System.out.println("   "+serviceRepos.get(i));
            }

        };
        nodeCache.getListenable().addListener(nodeCacheListener);
        nodeCache.start();
    }
}
