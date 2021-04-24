package com.guli.eduservice.mapper;

import com.guli.eduservice.pojo.CourseComment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.guli.eduservice.pojo.vo.CommentChildVo;

import java.util.List;

/**
 * <p>
 * 课程评论 Mapper 接口
 * </p>
 *
 * @author 叶子
 * @since 2021-04-22
 */
public interface CourseCommentMapper extends BaseMapper<CourseComment> {

    List<CommentChildVo> getChildList(String parentId);
}
