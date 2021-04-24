package com.guli.admin.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author 叶子
 * @Description 请设置
 * @PackageName com.guli.admin.client
 * @DevelopmentTools IntelliJ IDEA
 * @Data 2021/3/31 星期三 17:45
 */
@Component
@FeignClient("service-order")
public interface OrderClient {

    @GetMapping("/eduorder/orderdto/getPayStatus/{courseId}/{memberId}")
    public Boolean getPayStatus(@PathVariable String courseId,
                                @PathVariable String memberId);
}
