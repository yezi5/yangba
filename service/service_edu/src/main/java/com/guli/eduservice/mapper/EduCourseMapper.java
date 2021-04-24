package com.guli.eduservice.mapper;

import com.guli.eduservice.pojo.course.CourseListVo;
import com.guli.eduservice.pojo.course.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.guli.eduservice.pojo.front.CourseWebVo;
import com.guli.eduservice.pojo.vo.CourseInfoVo;
import com.guli.eduservice.pojo.vo.CoursePublishVo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author 叶子
 * @since 2020-10-06
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    CoursePublishVo getPublishCourseInfo(String courseId);

    CourseWebVo getBaseCourseInfo(String courseId);

    List<CourseListVo> selectList(HashMap<String, Object> map);

    List<CourseListVo> getCourseListVo(HashMap<String, Object> map);
}
