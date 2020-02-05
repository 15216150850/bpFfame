package com.bp.getway.config;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.ConfigurationBasedServerList;

/**
 * 配置自定义负载均衡
 * @author zxk
 */
//@RibbonClients(defaultConfiguration = ZuulBpdelopyRule.class)
public class RibbonClientDefaultConfigurationsConfig {

    public static class BazServiceList extends ConfigurationBasedServerList {

        public BazServiceList(IClientConfig config) {
            super.initWithNiwsConfig(config);
        }
    }
}




