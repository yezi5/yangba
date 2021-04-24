package com.guli.eduservice.service;

import com.guli.eduservice.pojo.CourseViewCount;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 课程访问量 服务类
 * </p>
 *
 * @author 叶子
 * @since 2021-04-04
 */
public interface CourseViewCountService extends IService<CourseViewCount> {

    /**
     * 指定访问量+1
     * 并返回指定课程访问量
     * @param courseId
     * @return
     */
    public long incr(String courseId);

    /**
     * 获取指定课程访问量
     * @param courseId
     * @return
     */
    public String getCount(String courseId);

    /**
     * 从缓存中拿出所有数据
     * @return
     */
    public Map<String, String> getDataFromRedis();

    /**
     * 将缓存刷回数据库
     */
    public void update();

    /**
     * 根据课程id获取课程浏览量记录
     * @param courseId
     * @return
     */
    public CourseViewCount getOne(String courseId);
}
