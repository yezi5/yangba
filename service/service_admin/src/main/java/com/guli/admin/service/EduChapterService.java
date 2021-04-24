package com.guli.admin.service;

import com.guli.admin.pojo.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.guli.admin.pojo.vo.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程章节 服务类
 * </p>
 *
 * @author 叶子
 * @since 2021-03-31
 */
public interface EduChapterService extends IService<EduChapter> {

    /**
     * 根据课程id获取课程所有章节
     * @param courseId 课程主键
     * @return 存储章节ViewModel对象（封装章节基本信息）的list集合
     */
    List<ChapterVo> getByCourseId(String courseId);

    /**
     * 根据课程id和用户ID获取课程所有章节
     * @param courseId 课程主键
     * @param userId   用户主键
     * @return  存储章节ViewModel对象（封装章节基本信息）的list集合
     */
    List<ChapterVo> getByCourseIdAndUserId(String courseId, String userId);

    /**
     * 根据课程id删除课程下所有章节
     * @param id 课程主键
     * @return 成功返回课程下所有章节主键的list集合，课程下无章节返回null
     */
    List<String> removeByCourseId(String id);
}
