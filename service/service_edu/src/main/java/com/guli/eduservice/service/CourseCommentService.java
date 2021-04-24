package com.guli.eduservice.service;

import com.guli.eduservice.pojo.CourseComment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.guli.eduservice.pojo.para.PublishCommentPara;

import java.util.Map;

/**
 * <p>
 * 课程评论 服务类
 * </p>
 *
 * @author 叶子
 * @since 2021-04-22
 */
public interface CourseCommentService extends IService<CourseComment> {

    boolean save(PublishCommentPara para, String userId);

    Map<String, Object> pageList(Long page, Long limit, String videoSourceId);
}
