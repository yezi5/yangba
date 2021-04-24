package com.guli.eduservice.client.impl;

import com.guli.commonutils.R;
import com.guli.eduservice.client.OssClient;
import org.springframework.stereotype.Component;

/**
 * @author 叶子
 * @Description 请设置
 * @PackageName com.guli.eduservice.client.impl
 * @DevelopmentTools IntelliJ IDEA
 * @Data 2021/3/22 星期一 20:27
 */
@Component
public class OssClientImpl implements OssClient {
    @Override
    public R deleteFile(String fileName) {
        return R.error().message("资源不存在");
    }
}
