package com.guli.eduservice.mapper;

import com.guli.eduservice.pojo.course.EduCourseCollect;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 课程收藏 Mapper 接口
 * </p>
 *
 * @author 叶子
 * @since 2020-10-06
 */
public interface EduCourseCollectMapper extends BaseMapper<EduCourseCollect> {

    List<String> selectCourseIds(String userId);
}
