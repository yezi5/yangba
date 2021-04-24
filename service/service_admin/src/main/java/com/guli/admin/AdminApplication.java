package com.guli.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author 叶子
 * @Description 请设置
 * @PackageName com.guli.admin
 * @DevelopmentTools IntelliJ IDEA
 * @Data 2021/3/30 星期二 15:26
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.guli")
@EnableFeignClients
@EnableDiscoveryClient
public class AdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(
                AdminApplication.class,
                args
        );
    }
}
