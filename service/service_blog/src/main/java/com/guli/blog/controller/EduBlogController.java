package com.guli.blog.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guli.blog.pojo.EduBlog;
import com.guli.blog.pojo.para.InsertBlogInfo;
import com.guli.blog.pojo.para.UpdateBlogInfo;
import com.guli.blog.pojo.vo.BlogListVo;
import com.guli.blog.pojo.vo.BlogUpdateVo;
import com.guli.blog.pojo.vo.BlogVo;
import com.guli.blog.service.EduBlogService;
import com.guli.commonutils.JwtUtils;
import com.guli.commonutils.R;
import com.guli.commonutils.ResultCode;
import com.guli.servicebase.util.RedisClient;
import com.guli.servicebase.util.RedisKey;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 博客 前端控制器
 * </p>
 *
 * @author 叶子
 * @since 2021-03-24
 */
@RestController
@RequestMapping("/blog")
@CrossOrigin(allowCredentials = "true")
@Api(tags = "博客处理器")
public class EduBlogController {

    @Autowired
    private EduBlogService blogService;
    @Autowired
    private RedisClient redisClient;

    @PostMapping("addInfo")
    @ApiOperation("发布博客")
    public R create(@RequestBody InsertBlogInfo blogInfo, HttpServletRequest request){
        String userId = JwtUtils.getMemberIdByJwtToken(request);
        if (StringUtils.isEmpty(userId)){
            return R.error().code(ResultCode.NOT_LOGIN).message("请登陆后再进行访问");
        }
        boolean rs = blogService.add(blogInfo,userId);
        return rs?R.ok():R.error();
    }

    @PostMapping("search/{page}/{limit}")
    @ApiOperation("分页查询+条件查询")
    public R find(@PathVariable Long page,
                  @PathVariable Long limit,
                  @RequestBody Map<String,Object> map){
        String target = (String) map.get("target");
        Page<EduBlog> pagePara = new Page<>(page,limit);
        Map<String, Object> rsMap = blogService.pageQuery(pagePara,target);

        return R.ok().data(rsMap);
    }

    @GetMapping("getInfo/{blogId}")
    public R getInfo(@PathVariable String blogId){
        BlogVo blogVo = blogService.getBlog(blogId);
        if (blogVo == null){
            return R.error().message("该资源不存在");
        }

        return R.ok().data("blog",blogVo);
    }

    @GetMapping("myBlog")
    public R getMyBlog(){
        List<BlogListVo> list = blogService.getMyBlog();
        if (list == null){
            return R.error().code(ResultCode.NOT_LOGIN);
        }
        return R.ok().data("items",list).data("total",list.size());
    }


    @PostMapping("update")
    public R update(@RequestBody UpdateBlogInfo blogInfo){
        boolean rs = blogService.update(blogInfo);

        return rs?R.ok():R.error();
    }

    @GetMapping("getUpdateInfo/{blogId}")
    public R getUpdateInfo(@PathVariable String blogId){
        BlogUpdateVo blogUpdateVo = blogService.getBlogUpdateVo(blogId);
        if (blogUpdateVo == null){
            return R.error();
        }
        return R.ok().data("blogInfo",blogUpdateVo);
    }

    @DeleteMapping("delete/{blogId}")
    public R delete(@PathVariable String blogId){
        boolean rs = blogService.remove(blogId);
        return rs?R.ok():R.error();
    }

    @GetMapping("/download/{blogId}")
    public void download(@PathVariable String blogId, HttpServletResponse response){
        blogService.download(blogId,response);
    }
}

