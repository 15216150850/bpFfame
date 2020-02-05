package com.bp.act;

import com.bp.act.config.ActBpdelopyRule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

/**
 * 工作流工具服务启动类
 *
 * @author 钟欣凯
 */
@SpringBootApplication
@EnableAutoConfiguration(exclude = {
        org.activiti.spring.boot.SecurityAutoConfiguration.class,
})
@ComponentScan({"com.bp.act.*", "com.bp.act.listener"})
@EnableEurekaClient
@EnableFeignClients
@ServletComponentScan
//@RibbonClient(name = "act",configuration = ActBpdelopyRule.class)
public class ActApplication {

    public static void main(String[] args) {
        SpringApplication.run(ActApplication.class, args);
    }

}
