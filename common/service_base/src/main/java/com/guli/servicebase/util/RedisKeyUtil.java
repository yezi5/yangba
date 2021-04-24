package com.guli.servicebase.util;

/**
 * @author 叶子
 * @Description 请设置
 * @DevelopmentTools IntelliJ IDEA
 * @Data 2021/4/5 星期一 13:02
 */
public class RedisKeyUtil {
    private static final String SEPARATE = "::";

    public static String key(String prefix, String key){
        return prefix+SEPARATE+key;
    }

    public static String getKey(String... ofKey){
        String key = ofKey[0];

        for (int i = 1; i < ofKey.length; i++) {
            key+=("::"+ofKey[i]);
        }
        return key;
    }

    public static String[] parseKey(String key){
        return key.split(SEPARATE);
    }
}
