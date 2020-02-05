package com.bp.euraker.euraker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurakerApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurakerApplication.class, args);
    }
}
