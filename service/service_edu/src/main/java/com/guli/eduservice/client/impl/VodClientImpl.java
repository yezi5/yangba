package com.guli.eduservice.client.impl;

import com.guli.commonutils.R;
import com.guli.eduservice.client.VodClient;
import org.springframework.stereotype.Component;

@Component
public class VodClientImpl implements VodClient {
    @Override
    public R removeVideo(String videoId) {
        return R.error().message("删除视频请求超时");
    }
}
