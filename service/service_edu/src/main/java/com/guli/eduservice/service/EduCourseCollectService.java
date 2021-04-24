package com.guli.eduservice.service;

import com.guli.eduservice.pojo.course.EduCourseCollect;
import com.baomidou.mybatisplus.extension.service.IService;
import com.guli.eduservice.pojo.vo.CollectCourseListVo;

import java.util.List;

/**
 * <p>
 * 课程收藏 服务类
 * </p>
 *
 * @author 叶子
 * @since 2020-10-06
 */
public interface EduCourseCollectService extends IService<EduCourseCollect> {

    /**
     * 创建收藏信息
     * @param courseId
     * @param token
     * @return
     */
    boolean star(String courseId, String token);

    /**
     * 根据课程id和用户id删除收藏课程
     * @param courseId
     * @return
     */
    boolean removeByCourseId(String courseId, String token);

    /**
     * 根据token获取用户收藏课程列表
     * @param token
     * @return
     */
    List<CollectCourseListVo> getStarListByToken(String token);

    /**
     * 判断用户受否收藏某课程
     * @param courseId
     * @param userId
     * @return
     */
    boolean isStar(String courseId, String userId);

    /**
     * 缓存持久化
     */
    public void update();
}
