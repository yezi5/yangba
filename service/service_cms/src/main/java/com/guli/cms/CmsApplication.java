package com.guli.cms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.guli")
@EnableDiscoveryClient
public class CmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(
                CmsApplication.class,
                args
        );
    }
}
