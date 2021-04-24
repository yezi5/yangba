package com.guli.admin.client.impl;

import com.guli.admin.client.VodClient;
import com.guli.commonutils.R;
import org.springframework.stereotype.Component;

/**
 * @author 叶子
 * @Description 请设置
 * @PackageName com.guli.admin.client.impl
 * @DevelopmentTools IntelliJ IDEA
 * @Data 2021/3/31 星期三 17:48
 */
@Component
public class VodClientImpl implements VodClient {
    @Override
    public R removeVideo(String videoId) {
        return R.error().message("删除视频请求超时");
    }
}
