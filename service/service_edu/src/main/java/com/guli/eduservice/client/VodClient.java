package com.guli.eduservice.client;

import com.guli.commonutils.R;
import com.guli.eduservice.client.impl.VodClientImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 *
 * @FeignClient(value = "service-vod",fallback = VodClientImpl.class)
 *  指定服务的提供者，fallback会在服务无法正常调用时执行
 */
@FeignClient(value = "service-vod",fallback = VodClientImpl.class)
@Component
public interface VodClient {

    @DeleteMapping("/eduvod/video/removeAlyVideo/{videoId}")
    public R removeVideo(@PathVariable("videoId") String videoId);


}
