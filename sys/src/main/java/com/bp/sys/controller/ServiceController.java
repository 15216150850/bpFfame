package com.bp.sys.controller;

import com.bp.common.bean.ReturnBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 服务相关信息控制层
 *
 * @auther: 钟欣凯
 * @date: 2019/5/23 14:01
 */
@RestController
public class ServiceController {
    @Autowired
    private DiscoveryClient discoveryClient;


    @PreAuthorize("hasAuthority('system:service:list')")
    @GetMapping("system/service/list")
    public ReturnBean<List<Map<String, Object>>> serviceList(@RequestParam Map para) {
        List<String> services = discoveryClient.getServices();
        List<Map<String, Object>> list = new ArrayList<>();
        for (String serviceId : services) {
            List<ServiceInstance> instances = discoveryClient.getInstances(serviceId);

            for (ServiceInstance serviceInstance :
                    instances) {
                Map<String, Object> serviceInstanceMap = new HashMap<>();
                serviceInstanceMap.put("serviceId", serviceInstance.getServiceId());
                serviceInstanceMap.put("port", serviceInstance.getPort());

                serviceInstanceMap.put("host", serviceInstance.getHost());
                list.add(serviceInstanceMap);
            }
        }

        return ReturnBean.list(list, (long) list.size());
    }
}
