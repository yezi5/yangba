package com.guli.msm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.guli")
@EnableDiscoveryClient
public class MsmApplication {

    public static void main(String[] args) {
        SpringApplication.run(
                MsmApplication.class,
                args
        );
    }
}
