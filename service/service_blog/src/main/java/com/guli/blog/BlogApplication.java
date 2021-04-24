package com.guli.blog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author 叶子
 * @Description 请设置
 * @PackageName com.guli.blog
 * @DevelopmentTools IntelliJ IDEA
 * @Data 2021/3/24 星期三 17:50
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.guli"})
@MapperScan("com.guli.blog.mapper")
@EnableDiscoveryClient  //注册服务，服务提供客户端
@EnableFeignClients     //服务调用者，使用服务的客户端
public class BlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(
                BlogApplication.class,
                args
        );
    }
}
