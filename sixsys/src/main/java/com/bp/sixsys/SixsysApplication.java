package com.bp.sixsys;

import com.codingapi.txlcn.tc.config.EnableDistributedTransaction;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ApplicationContext;

/**
 * 六大体系
 * @author zxk
 *
 */
@SpringBootApplication
@EnableFeignClients
@EnableEurekaClient
//@EnableDistributedTransaction
public class SixsysApplication {
    
    private static ApplicationContext applicationContext;

    public static void main(String[] args) {
        applicationContext = SpringApplication.run(SixsysApplication.class, args);
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

}
