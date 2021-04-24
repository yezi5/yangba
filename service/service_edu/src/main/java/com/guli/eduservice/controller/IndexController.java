package com.guli.eduservice.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.guli.commonutils.R;
import com.guli.eduservice.pojo.EduTeacher;
import com.guli.eduservice.pojo.course.CourseListVo;
import com.guli.eduservice.service.EduCourseService;
import com.guli.eduservice.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *
 * @author 叶子
 * @since 2020/10/15
 */
@RestController
@RequestMapping("/eduservice/indexfront")
@CrossOrigin(allowCredentials = "true")
@Api(tags = "前台系统首页控制器")
public class IndexController {
    @Autowired
    private EduCourseService courseService;
    @Autowired
    private EduTeacherService teacherService;

    @ApiOperation(value = "查询首页数据")
    @GetMapping("index")
    public R getIndexData(){
        List<CourseListVo> courseList = courseService.listIndex(8);

        QueryWrapper<EduTeacher> teacherQueryWrapper = new QueryWrapper<>();
        teacherQueryWrapper.orderByDesc("sort");
        teacherQueryWrapper.last("limit 8");
        List<EduTeacher> teacherList = teacherService.list(teacherQueryWrapper);

        return R.ok().data("eduList",courseList).data("teacherList",teacherList);
    }
}
