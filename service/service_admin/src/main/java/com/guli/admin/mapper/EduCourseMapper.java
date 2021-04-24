package com.guli.admin.mapper;

import com.guli.admin.pojo.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.guli.admin.pojo.vo.CoursePublishVo;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author 叶子
 * @since 2021-03-30
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    CoursePublishVo getPublishCourseInfo(String courseId);
}
