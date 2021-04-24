package com.guli.eduservice.mapper;

import com.guli.eduservice.pojo.CourseViewCount;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.Map;

/**
 * <p>
 * 课程访问量 Mapper 接口
 * </p>
 *
 * @author 叶子
 * @since 2021-04-04
 */
public interface CourseViewCountMapper extends BaseMapper<CourseViewCount> {

    Integer update(Map<String, String> map);
}
