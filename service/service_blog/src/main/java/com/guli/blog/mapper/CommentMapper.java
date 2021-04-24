package com.guli.blog.mapper;

import com.guli.blog.pojo.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.guli.blog.pojo.vo.CommentChildVo;

import java.util.List;

/**
 * <p>
 * 博客评论 Mapper 接口
 * </p>
 *
 * @author 叶子
 * @since 2021-04-05
 */
public interface CommentMapper extends BaseMapper<Comment> {

    List<CommentChildVo> getChildList(String parentId);
}
