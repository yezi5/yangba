package com.guli.eduservice.controller;


import com.guli.commonutils.R;
import com.guli.commonutils.ResultCode;
import com.guli.eduservice.pojo.vo.CollectCourseListVo;
import com.guli.eduservice.service.EduCourseCollectService;
import com.guli.servicebase.annotation.Limit;
import com.guli.servicebase.util.RedisClient;
import com.guli.servicebase.util.RedisKey;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 课程收藏 前端控制器
 * </p>
 *
 * @author 叶子
 * @since 2020-10-06
 */
@RestController
@RequestMapping("/eduservice/collect")
@CrossOrigin(allowCredentials = "true")
@Api(tags = "课程收藏")
public class EduCourseCollectController {

    @Autowired
    private EduCourseCollectService collectService;

    @Autowired
    private RedisClient redis;

    @GetMapping("star/{courseId}")
    @Limit(period = 60,count = 8,banTime = 30)
    public R star(@PathVariable String courseId, HttpServletRequest request){
        String token = request.getHeader(RedisKey.TOKEN_KEY);
        if (StringUtils.isEmpty(token)){
            return R.error().code(ResultCode.NOT_LOGIN);
        }
        boolean rs = collectService.star(courseId,token);

        return rs?R.ok():R.error();
    }

    @GetMapping("removeStar/{courseId}")
    @Limit(period = 60,count = 8,banTime = 30)
    public R removeStar(@PathVariable String courseId, HttpServletRequest request){
        String token = request.getHeader(RedisKey.TOKEN_KEY);
        boolean rs = collectService.removeByCourseId(courseId,token);
        return rs?R.ok():R.error();
    }

    @GetMapping("getStars")
    public R getStars(HttpServletRequest request){

        String token = request.getHeader(RedisKey.TOKEN_KEY);
        List<CollectCourseListVo> list = collectService.getStarListByToken(token);

        return R.ok().data("items",list).data("total",list.size());
    }
}

