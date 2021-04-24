package com.guli.eduservice.client;

import com.guli.commonutils.R;
import com.guli.eduservice.client.impl.OssClientImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author 叶子
 * @Description 请设置
 * @PackageName com.guli.eduservice.client
 * @DevelopmentTools IntelliJ IDEA
 * @Data 2021/3/22 星期一 19:32
 */
@Component
@FeignClient(value = "service-oss", fallback = OssClientImpl.class)
public interface OssClient {

    @DeleteMapping("/eduoss/fileoss/delete/{fileName}")
    public R deleteFile(@PathVariable String fileName);
}
