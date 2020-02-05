package com.bp.getway.config;

import com.bp.common.interface_.DeloMyIp;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.ribbon.SpringClientFactory;

import java.util.List;

/**
 * 开发阶段负载均衡策略,目地是使各个程序员的微服务不冲突
 * @auther: 钟欣凯
 * @date: 2019/6/20 08:35
 */

//@Configuration
public class ZuulBpdelopyRule extends AbstractLoadBalancerRule {

    public static String ip;
    @Override
    public void initWithNiwsConfig(IClientConfig iClientConfig) {
        System.out.println();
    }

    @Autowired
    private SpringClientFactory getLoadBalancer;

    @Override
    public Server choose(Object o) {
        ILoadBalancer loadBalancer = getLoadBalancer();
        System.out.println(getLoadBalancer);
//        Server server = new Server()
        List<Server>   serverList = null;
        if (o != null){

            ILoadBalancer o1 = getLoadBalancer.getLoadBalancer(o.toString());
            serverList =  o1.getAllServers();
        } else {

            serverList = loadBalancer.getReachableServers();
        }



        Server returnServer = null;
        System.out.println("进入自定义负载均衡了\n");
        for(Server server: serverList){
            String appName = server.getMetaInfo().getAppName();
            if ("ACT".equalsIgnoreCase(appName)||"LOG".equalsIgnoreCase(appName)||"FILE".equalsIgnoreCase(appName)
                    ||"zuul".equalsIgnoreCase(appName)||"oauth".equalsIgnoreCase(appName)){

                return  server;

            } else {
                String host = server.getHost();

//                if (ip == null){
//                    ip = "192.168.0.121";
//                }
                if ("127.0.0.1".equals(ip)){
                    ip = "30.129.1.224";
                }
                if (ip!=null){

                    if (server.getHost().equals(ip)){

                        returnServer = server;
                        break;
                    }

                } else {

                    if (server.getHost().equals(DeloMyIp.getMyIp())){
                        returnServer = server;
                        break;
                    }
                }

            }

        }
           return returnServer;
    }

}
