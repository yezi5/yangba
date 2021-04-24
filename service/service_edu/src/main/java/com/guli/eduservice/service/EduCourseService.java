package com.guli.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guli.eduservice.pojo.course.CourseListVo;
import com.guli.eduservice.pojo.course.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.guli.eduservice.pojo.front.CourseFrontVo;
import com.guli.eduservice.pojo.front.CourseWebVo;
import com.guli.eduservice.pojo.order.OrderDto;
import com.guli.eduservice.pojo.vo.CourseQuery;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author 叶子
 * @since 2020-10-06
 */
public interface EduCourseService extends IService<EduCourse> {



    /**
     * 分页查询+条件查询 课程信息
     * @param pagePara 分页参数
     * @param courseQuery 条件查询参数
     */
    void pageQuery(Page<EduCourse> pagePara, CourseQuery courseQuery);


    Map<String, Object> getCourseFrontList(Page<EduCourse> pageParam, CourseFrontVo courseFrontVo);

    CourseWebVo getBaseCourseInfo(String courseId);

    OrderDto getOrderDto(String courseId);

    /**
     * 查询指定条数据
     * @param count
     * @return
     */
    List<CourseListVo> listIndex(int count);

    List<CourseListVo> getCourseListVo(String teacherId);

    /**
     * 为视频播放页获取视频基本信息
     * @param courseId
     * @return
     */
    CourseWebVo getBaseCourseInfoForPlayer(String courseId);
}
