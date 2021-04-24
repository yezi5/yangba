package com.guli.blog.service;

/**
 * @author 叶子
 * @Description 请设置
 * @PackageName com.guli.blog.service
 * @DevelopmentTools IntelliJ IDEA
 * @Data 2021/3/25 星期四 13:39
 */
public interface UcenterService {

    /**
     * 根据用户id获取用户名
     * @param memberId
     * @return
     */
    String getMemberName(String memberId);
}
