package com.guli.blog.service;

import com.guli.blog.pojo.Comment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.guli.blog.pojo.para.PublishCommentPara;
import com.guli.blog.pojo.vo.CommentVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 博客评论 服务类
 * </p>
 *
 * @author 叶子
 * @since 2021-04-05
 */
public interface CommentService extends IService<Comment> {

    /**
     * 发表评论
     * @param para
     * @param userId
     * @return
     */
    boolean save(PublishCommentPara para, String userId);

    /**
     * 查询评论列表
     * @param page
     * @param limit
     * @param blogId
     * @return
     */
    Map<String, Object> pageList(Long page, Long limit, String blogId);
}
