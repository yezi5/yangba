package com.guli.admin.client.impl;

import com.guli.admin.client.OssClient;
import com.guli.commonutils.R;
import org.springframework.stereotype.Component;

/**
 * @author 叶子
 * @Description 请设置
 * @PackageName com.guli.admin.client.impl
 * @DevelopmentTools IntelliJ IDEA
 * @Data 2021/3/31 星期三 17:56
 */
@Component
public class OssClientImpl implements OssClient {
    @Override
    public R deleteFile(String fileName) {
        return R.error().message("资源不存在");
    }
}
