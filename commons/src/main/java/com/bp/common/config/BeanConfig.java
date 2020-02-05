package com.bp.common.config;

import com.bp.common.utils.IdWorker;
import com.netflix.loadbalancer.IRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @authOr: 钟欣凯
 * @date: 2019/3/15 09:20
 */
@Configuration
public class BeanConfig {


    @Bean
    public IdWorker idWorker(){

        return new IdWorker(1,1);
    }



}
