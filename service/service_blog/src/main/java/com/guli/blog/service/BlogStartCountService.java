package com.guli.blog.service;

/**
 * @author 叶子
 * @Description 请设置
 * @DevelopmentTools IntelliJ IDEA
 * @Data 2021/4/5 星期一 13:47
 */
public interface BlogStartCountService {

    /**
     * 收藏量+1，并返回当前收藏量
     * @param blogId
     * @return
     */
    Long incr(String blogId);

    /**
     * 收藏量-1，并返回当前收藏量
     * @param blogId
     * @return
     */
    Long decr(String blogId);

    /**
     * 获取当前收藏量
     * @param blogId
     * @return
     */
    Long getCount(String blogId);

    /**
     * 将缓存刷新到数据库
     */
    void update();
}
