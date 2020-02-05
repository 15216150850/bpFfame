package com.bp.common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @auther: 钟欣凯
 * @date: 2019/6/21 19:21
 */
@Component
@EnableConfigurationProperties({ZuulProperties.class})
public class DynamicRouting {

    @Autowired
    private ZuulProperties zuulProperties;

    @Autowired
    DiscoveryClient discoveryClient;

    @PostConstruct
    public void init() {

// Get all services from Eureka

        List<String> allServices = discoveryClient.getServices();

        String prefix = "192.168.0.121"+".";

        for(String service : allServices) {

// If a service starts with my designated prefix, replace the original route to it

            if (service.startsWith(prefix)) {

                String originalService = service.substring(service.indexOf(".")+1);

                for(ZuulProperties.ZuulRoute route : zuulProperties.getRoutes().values())

                {

                    if (route.getServiceId().equals(originalService)) {

// Change original route to ‘my’ service id

                        route.setServiceId(service);

                    }
                }
            }
        }
    }
}
