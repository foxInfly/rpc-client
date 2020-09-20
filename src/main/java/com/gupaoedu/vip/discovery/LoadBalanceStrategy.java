package com.gupaoedu.vip.discovery;

import java.util.List;

public interface LoadBalanceStrategy {

    String selectHost(List<String> repos);

}
