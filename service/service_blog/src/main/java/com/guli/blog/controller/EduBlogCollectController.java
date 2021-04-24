package com.guli.blog.controller;


import com.guli.blog.pojo.vo.BlogListVo;
import com.guli.blog.service.EduBlogCollectService;
import com.guli.commonutils.JwtUtils;
import com.guli.commonutils.R;
import com.guli.commonutils.ResultCode;
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
 * 博客收藏表 前端控制器
 * </p>
 *
 * @author 叶子
 * @since 2021-03-27
 */
@RestController
@RequestMapping("/blog/collect")
@CrossOrigin(allowCredentials = "true")
@Api(tags = "博客收藏处理器")
public class EduBlogCollectController {

    @Autowired
    private EduBlogCollectService collectService;
    @Autowired
    private RedisClient redis;

    @GetMapping("star/{blogId}")
    @Limit(period = 60, count = 10)
    public R star(@PathVariable String blogId, HttpServletRequest request){
        String userId = JwtUtils.getMemberIdByJwtToken(request);
        if (StringUtils.isEmpty(userId)) return R.error().code(ResultCode.NOT_LOGIN);

        boolean rs = collectService.star(blogId,userId);

        return rs?R.ok():R.error();
    }

    @DeleteMapping("removeStar/{blogId}")
    @Limit(period = 60, count = 9)
    public R removeStar(@PathVariable String blogId, HttpServletRequest request){
        String userId = JwtUtils.getMemberIdByJwtToken(request);
        if (StringUtils.isEmpty(userId)) return R.error().code(ResultCode.NOT_LOGIN);
        boolean rs = collectService.removeStar(blogId,userId);
        return rs?R.ok():R.error();
    }

    @GetMapping
    public R getStars(HttpServletRequest request){
        String userId = JwtUtils.getMemberIdByJwtToken(request);
        if (StringUtils.isEmpty(userId)) return R.error().code(ResultCode.NOT_LOGIN);
        List<BlogListVo> list = collectService.getStars(userId);

        return R.ok().data("items",list).data("total",list.size());
    }
}

