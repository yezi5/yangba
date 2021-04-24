package com.guli.admin.client;

import com.guli.admin.client.impl.VodClientImpl;
import com.guli.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author 叶子
 * @Description 请设置
 * @PackageName com.guli.admin.client
 * @DevelopmentTools IntelliJ IDEA
 * @Data 2021/3/31 星期三 17:47
 */
@FeignClient(value = "service-vod",fallback = VodClientImpl.class)
@Component
public interface VodClient {

    @DeleteMapping("/eduvod/video/removeAlyVideo/{videoId}")
    public R removeVideo(@PathVariable("videoId") String videoId);


}
