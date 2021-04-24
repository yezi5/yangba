package com.guli.blog.service;

import com.guli.blog.pojo.EduBlogCollect;
import com.baomidou.mybatisplus.extension.service.IService;
import com.guli.blog.pojo.vo.BlogListVo;

import java.util.List;

/**
 * <p>
 * 博客收藏表 服务类
 * </p>
 *
 * @author 叶子
 * @since 2021-03-27
 */
public interface EduBlogCollectService extends IService<EduBlogCollect> {

    /**
     * 创建收藏信息
     * @param blogId
     * @param userId
     * @return
     */
    boolean star(String blogId, String userId);

    /**
     * 删除收藏信息
     * @param blogId
     * @return
     */
    boolean removeStar(String blogId, String userId);

    /**
     * 从redis中获取收藏记录
     * @return
     */
    List<EduBlogCollect> getDataFromRedis();

    /**
     * 获取收藏博客列表
     * @return
     */
    List<BlogListVo> getStars(String userId);

    /**
     * 获取用户收藏博客id列表
     * @param userId
     * @return
     */
    List<String> getBlogIds(String userId);

    /**
     * 更新缓存到数据库
     */
    void update();

    /**
     * 根据userId和blogId删除记录
     * @param userId
     * @param blogId
     * @return
     */
    boolean deleteByUserIdAndBlogId(String userId, String blogId);
}
