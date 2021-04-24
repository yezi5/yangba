package com.guli.eduservice.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guli.commonutils.JwtUtils;
import com.guli.commonutils.R;
import com.guli.eduservice.client.OrderClient;
import com.guli.eduservice.pojo.course.EduCourse;
import com.guli.eduservice.pojo.front.CourseFrontVo;
import com.guli.eduservice.pojo.front.CourseWebVo;
import com.guli.eduservice.pojo.vo.ChapterVo;
import com.guli.eduservice.pojo.vo.CoursePara;
import com.guli.eduservice.service.*;
import com.guli.servicebase.util.RedisClient;
import com.guli.servicebase.util.RedisKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/eduservice/coursefront")
@CrossOrigin(allowCredentials = "true")
public class CourseFrontController {

    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduChapterService chapterService;

    @Autowired
    private EduVideoService videoService;

    @Autowired
    private RedisClient redis;
    @Autowired
    private EduCourseCollectService collectService;
    @Autowired
    private OrderClient orderClient;
    @Autowired
    private HistoryService historyService;

    //1 条件查询带分页查询课程
    @PostMapping("getFrontCourseList/{page}/{limit}")
    public R getFrontCourseList(@PathVariable Long page, @PathVariable Long limit,
                                @RequestBody(required = false) CourseFrontVo courseFrontVo) {
        Page<EduCourse> pageCourse = new Page<>(page,limit);
        Map<String,Object> map = courseService.getCourseFrontList(pageCourse,courseFrontVo);
        //返回分页所有数据
        return R.ok().data(map);
    }

    @GetMapping("getFrontCourseInfo/{id}")
    public R getFrontCourseInfo(@PathVariable String id,HttpServletRequest request){
        String token = request.getHeader("token");
        String userId = "";
        boolean isBuy = false;
        if (!StringUtils.isEmpty(token)){
            userId = JwtUtils.getMemberIdByJwtToken(token);
        }
        CourseWebVo courseWebVo = courseService.getBaseCourseInfo(id);
        if (StringUtils.isEmpty(userId)){
            courseWebVo.setStar(false);
        }else { // 用户登录状态
            courseWebVo.setStar(collectService.isStar(id,userId));
            isBuy = orderClient.getPayStatus(id, userId);

            // 添加浏览记录
            historyService.save(id,userId);
        }

        List<ChapterVo> chapterVideoList = chapterService.getByCourseIdAndUserId(id,userId);
        return R.ok().data("courseWebVo",courseWebVo).data("chapterVideoList",chapterVideoList).data("total",chapterVideoList.size()).data("isBuy",isBuy);
    }

    @GetMapping("getPlayerCourseInfo/{videoSourceId}")
    public R getPlayerCourseInfo(@PathVariable String videoSourceId,HttpServletRequest request){
        String userId = null;
        String token = request.getHeader("token");
        if (!StringUtils.isEmpty(token)){
            userId = JwtUtils.getMemberIdByJwtToken(token);
        }

        String courseId = videoService.getCourseIdByVideoCourseId(videoSourceId);
        if (!StringUtils.isEmpty(courseId)){
            CourseWebVo courseWebVo = courseService.getBaseCourseInfoForPlayer(courseId);

            List<ChapterVo> chapterVideoList = chapterService.getByCourseIdAndUserId(courseId,userId);
            return R.ok().data("courseInfo",courseWebVo).data("chapterVideoList",chapterVideoList);
        }
        return R.error().message("请求资源不存在！");
    }

}












