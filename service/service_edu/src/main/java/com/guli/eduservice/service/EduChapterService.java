package com.guli.eduservice.service;

import com.guli.eduservice.pojo.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.guli.eduservice.pojo.vo.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author 叶子
 * @since 2020-10-10
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

}
