package com.guli.blog.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.guli.blog.client.UserClient;
import com.guli.blog.service.UcenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * @author 叶子
 * @Description 请设置
 * @PackageName com.guli.blog.service.impl
 * @DevelopmentTools IntelliJ IDEA
 * @Data 2021/3/25 星期四 13:40
 */
@Service
public class UcenterServiceImpl implements UcenterService {

    @Autowired
    private UserClient client;

    @Override
    public String getMemberName(String memberId) {
        String member = client.getMember(memberId);
        if (StringUtils.isEmpty(member)){
            return null;
        }

        Map<String,Object> map = JSONObject.parseObject(member, Map.class);
        String nickname = (String) map.get("nickname");

        return nickname;
    }
}
