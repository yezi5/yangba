package com.guli.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guli.admin.pojo.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.guli.admin.pojo.info.CourseInfoVo;
import com.guli.admin.pojo.info.CourseQuery;
import com.guli.admin.pojo.vo.CoursePublishVo;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author 叶子
 * @since 2021-03-30
 */
public interface EduCourseService extends IService<EduCourse> {


    /**
     * 新增课程信息
     * @param courseInfoVo 封装课程信息的课程ViewModel对象
     * @return 课程主键
     */
    String saveCourse(CourseInfoVo courseInfoVo);

    /**
     * 获取课程信息
     * @param courseId 课程id
     * @return 课程ViewModel对象
     */
    CourseInfoVo getCourseInfo(String courseId);

    /**
     * 更新课程信息
     * @param courseInfo 封装课程信息的课程ViewModel对象
     * @return 课程主键
     */
    String updateCourseInfo(CourseInfoVo courseInfo);

    /**
     * 获取预备发布的课程的信息
     * @param courseId 课程主键
     * @return 封装预备发布课程信息的ViewModel对象
     */
    CoursePublishVo getPublishCourseInfo(String courseId);

    /**
     * 最终发布课程
     * @param id 课程id
     * @return 发布成功-->true 发布失败-->false
     */
    boolean publishCourse(String id);

    void pageQuery(Page<EduCourse> pagePara, CourseQuery courseQuery);
}
