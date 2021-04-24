package com.guli.eduservice.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author 叶子
 * @Description 请设置
 * @PackageName com.guli.blog.client
 * @DevelopmentTools IntelliJ IDEA
 * @Data 2021/3/25 星期四 13:26
 */
@Component
@FeignClient("service-ucenter")
public interface UserClient {

    @GetMapping("/ucenter/orderDto/getMember/{memberId}")
    public String getMember(@PathVariable String memberId);
}
