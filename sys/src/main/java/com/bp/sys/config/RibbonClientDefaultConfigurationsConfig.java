package com.bp.sys.config;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.ConfigurationBasedServerList;
import org.springframework.cloud.netflix.ribbon.RibbonClients;

/**
 * 配置自定义负载均衡
 * @author zxk
 */
@RibbonClients(defaultConfiguration = SysBpdelopyRule.class)
public class RibbonClientDefaultConfigurationsConfig {

    public static class BazServiceList extends ConfigurationBasedServerList {

        public BazServiceList(IClientConfig config) {
            super.initWithNiwsConfig(config);
        }
    }
}




