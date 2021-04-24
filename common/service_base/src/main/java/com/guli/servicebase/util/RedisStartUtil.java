package com.guli.servicebase.util;

import java.util.List;

/**
 * @author 叶子
 * @Description 请设置
 * @DevelopmentTools IntelliJ IDEA
 * @Data 2021/4/4 星期日 9:38
 */
public class RedisStartUtil {

    final static String SPLIT = "::";

    public static String getKey(String userId, String courseId){
        return userId+SPLIT+courseId;
    }

    /**
     * 将 key 解析成 userId  courseId
     * @param key
     * @return 0-> userId    1-> courseId
     */
    public static String[] parseKey(String key){
        return key.split(SPLIT);
    }
}
