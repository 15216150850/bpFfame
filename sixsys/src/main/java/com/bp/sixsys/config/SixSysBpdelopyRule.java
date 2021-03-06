package com.bp.sixsys.config;

import com.bp.common.constants.BpruleIpConstans;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.ribbon.SpringClientFactory;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Set;

/**
 * 开发阶段负载均衡策略,目地是使各个程序员的微服务不冲突
 * @author: 钟欣凯
 * @date: 2019/6/20 08:35
 */

@Configuration
public class SixSysBpdelopyRule extends AbstractLoadBalancerRule {

    public static String ip;
    @Override
    public void initWithNiwsConfig(IClientConfig iClientConfig) {
        System.out.println();
    }


    @Autowired
    private SpringClientFactory springClientFactory;
    @Override

    public Server choose(Object o) {
        ILoadBalancer loadBalancer = getLoadBalancer();
        System.out.println(springClientFactory);
        Set<String> contextNames = springClientFactory.getContextNames();

//        Server server = new Server()
        List<Server>   serverList = null;
        if (o == null ||"default".equals(o)){
            serverList = loadBalancer.getReachableServers();

        } else {
            ILoadBalancer o1 = springClientFactory.getLoadBalancer(o.toString());
            serverList =  o1.getAllServers();
        }
        Server returnServer = null;
        System.out.println("进入自定义负载均衡了\n");
        for(Server server: serverList){
            String appName = server.getMetaInfo().getAppName();
            if ("LOG".equalsIgnoreCase(appName) || "FILE".equalsIgnoreCase(appName)
                    || "zuul".equalsIgnoreCase(appName) || "oauth".equalsIgnoreCase(appName)) {

                return server;

            } else {
                if (ip == null) {
                    ip = BpruleIpConstans.MY_IP;
                }

                if (server.getHost().equals(ip)) {
                    returnServer = server;
                    break;
                }
            }

        }
           return returnServer;
    }


}
