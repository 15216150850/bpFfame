package com.bp.sys;


import com.bp.sys.config.SysBpdelopyRule;
import com.codingapi.txlcn.tc.config.EnableDistributedTransaction;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableFeignClients
@EnableEurekaClient
//@EnableDistributedTransaction
@EnableScheduling
@EnableCaching
public class SysApplication {

    public static void main(String[] args) {
        try {
            SpringApplication.run(SysApplication.class, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
