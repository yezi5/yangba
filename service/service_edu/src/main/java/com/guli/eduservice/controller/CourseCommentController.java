package com.guli.eduservice.controller;


import com.guli.commonutils.JwtUtils;
import com.guli.commonutils.R;
import com.guli.commonutils.ResultCode;
import com.guli.eduservice.pojo.para.PublishCommentPara;
import com.guli.eduservice.service.CourseCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 * 课程评论 前端控制器
 * </p>
 *
 * @author 叶子
 * @since 2021-04-22
 */
@RestController
@RequestMapping("/eduservice/courseComment")
@CrossOrigin(allowCredentials = "true")
public class CourseCommentController {

    @Autowired
    private CourseCommentService commentService;

    /**
     * 发布评论：
     *      1. 博客id
     *      2. 评论用户id
     *      3. 被评论ID --> 可以为空
     *      4. 评论内容
     *
     */

    @PostMapping("publish")
    public R publishComment(@RequestBody PublishCommentPara para, HttpServletRequest request){
        if ("OPTIONS".equals(request.getMethod())){
            return R.ok();
        }
        String userId = JwtUtils.getMemberIdByJwtToken(request);
        if (StringUtils.isEmpty(userId)){
            return R.error().code(ResultCode.NOT_LOGIN).message("请先登录");
        }
        boolean rs = commentService.save(para,userId);

        return rs?R.ok():R.error();
    }

    @GetMapping("commentList/{page}/{limit}/{videoSourceId}")
    public R commentList(@PathVariable Long page,
                         @PathVariable Long limit,
                         @PathVariable String videoSourceId,
                         HttpServletRequest request){
        if ("OPTIONS".equals(request.getMethod())){
            return R.ok();
        }
        Map<String, Object> map = commentService.pageList(page,limit,videoSourceId);

        return R.ok().data(map);
    }
}

