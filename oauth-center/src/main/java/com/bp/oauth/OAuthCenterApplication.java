package com.bp.oauth;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 认证中心
 * 
 * @author 钟欣凯
 *
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
//@RibbonClient(name = "oauth",configuration = BpRuleConfig.class)
public class OAuthCenterApplication {

	public static void main(String[] args) {
		SpringApplication.run(OAuthCenterApplication.class, args);
	}

}