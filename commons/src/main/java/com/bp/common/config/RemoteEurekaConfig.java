package com.bp.common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.cloud.netflix.eureka.EurekaInstanceConfigBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;

/**
 * @auther: 钟欣凯
 * @date: 2019/6/21 19:17
 */
//@Configuration
public class RemoteEurekaConfig {


    @Value("${dev.discovery:true}")
    private Boolean devDiscovery;
    @Bean
    @Autowired
    @Profile("development")
    public EurekaInstanceConfigBean eurekaInstanceConfigBean(final InetUtils inetUtils) {

        String newAppName = "192.168.0.121" + "." + "sys";

        EurekaInstanceConfigBean    config = new EurekaInstanceConfigBean(inetUtils) {

            @Override

            public void setEnvironment(Environment environment) {

                super.setEnvironment(environment);

                if (devDiscovery != null && devDiscovery == true) {

                    setAppname(newAppName);

                    setVirtualHostName(newAppName);

                    setSecureVirtualHostName(newAppName);

                }

            }

        };

        config.setNonSecurePort(9001);

        config.setIpAddress("192.168.0.121");

        config.getMetadataMap().put("instanceId", config.getHostname() + ":" + config.getAppname() + ":" + 9001);

        return config;

    }
}
