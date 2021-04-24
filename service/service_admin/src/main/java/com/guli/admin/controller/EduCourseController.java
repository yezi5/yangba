package com.guli.admin.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guli.admin.client.OssClient;
import com.guli.admin.client.VodClient;
import com.guli.admin.pojo.EduCourse;
import com.guli.admin.pojo.info.CourseInfoVo;
import com.guli.admin.pojo.info.CourseQuery;
import com.guli.admin.pojo.vo.CoursePublishVo;
import com.guli.admin.service.EduChapterService;
import com.guli.admin.service.EduCourseDescriptionService;
import com.guli.admin.service.EduCourseService;
import com.guli.admin.service.EduVideoService;
import com.guli.commonutils.R;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author 叶子
 * @since 2021-03-30
 */
@RestController
@RequestMapping("/admin/course")
@CrossOrigin(allowCredentials = "true")
public class EduCourseController {

    @Autowired
    private EduCourseService courseService;
    @Autowired
    private EduCourseDescriptionService descriptionService;
    @Autowired
    private EduChapterService chapterService;
    @Autowired
    private EduVideoService videoService;
    @Autowired
    private VodClient vodClient;
    @Autowired
    private OssClient ossClient;

    @ApiOperation(value = "添加课程信息")
    @PostMapping("addCourseInfo")
    public R addCourseInfo(@ApiParam(name = "封装的课程对象",value = "课程VM对象",required = true) @RequestBody CourseInfoVo courseInfoVo){
        String courseId = courseService.saveCourse(courseInfoVo);

        return R.ok().data("courseId",courseId);
    }

    @ApiOperation(value = "获取课程信息")
    @GetMapping("getCourseInfo/{id}")
    public R getCourseInfo(@ApiParam(name = "课程id",value = "课程主键",required = true) @PathVariable String id){
        CourseInfoVo courseInfoVo = courseService.getCourseInfo(id);

        return R.ok().data("courseInfoVo",courseInfoVo);
    }

    @ApiOperation(value = "修改课程信息")
    @PostMapping("updateCourseInfo")
    public R updateCourseInfo(@ApiParam(name = "封装的课程对象",value = "课程VM对象",required = true) @RequestBody CourseInfoVo courseInfo){
        String courseId = courseService.updateCourseInfo(courseInfo);
        return R.ok().data("courseId",courseId);
    }

    @ApiOperation(value = "根据课程id获取准备发布的课程信息")
    @GetMapping("getPublishCourseInfo/{courseId}")
    public R getPublishCourseInfo(@ApiParam(name = "课程id",value = "课程主键",required = true) @PathVariable String courseId){
        CoursePublishVo coursePublishVo = courseService.getPublishCourseInfo(courseId);
        return R.ok().data("publishCourse",coursePublishVo);
    }

    @ApiOperation(value = "最终发布课程")
    @PostMapping("publishCourse/{id}")
    public R publishCourse(@ApiParam(name = "课程id",value = "课程主键",required = true) @PathVariable String id){
        boolean b = courseService.publishCourse(id);
        if (b){
            return R.ok();
        }
        return R.error();
    }

    @ApiOperation(value = "分页+条件查询课程信息")
    @PostMapping("getListCourse/{page}/{limit}")
    public R getListCourse(@ApiParam(name = "当前页数",value = "当前所在页数",required = true)@PathVariable Long page,
                           @ApiParam(name = "每页数据量",value = "每页数据量",required = true)@PathVariable Long limit,
                           @ApiParam(name = "封装的查询条件对象",value = "查询条件",required = false)@RequestBody(required = false) CourseQuery courseQuery){

        Page<EduCourse> pagePara = new Page<>(page,limit);
        courseService.pageQuery(pagePara,courseQuery);
        List<EduCourse> list = pagePara.getRecords();
        long total = pagePara.getTotal();

        return R.ok().data("list",list).data("total",total);
    }

    @ApiOperation(value = "删除课程信息")
    @DeleteMapping("deleteById/{id}")
    @Transactional
    public R deleteById(@ApiParam(name = "课程id",value = "课程主键",required = true) @PathVariable String id){
        if (!StringUtils.isEmpty(id)){
            // 获取课程封面
            EduCourse eduCourse = courseService.getById(id);
            String cover = eduCourse.getCover();
            String fileName = cover.substring("https://edu-guli1020.oss-cn-beijing.aliyuncs.com/".length());

            //删除课程主体信息（课程表）
            courseService.removeById(id);
            //删除课程简介（简介表）
            descriptionService.removeById(id);

            //删除章节
            List<String> chapterIdList = chapterService.removeByCourseId(id);
            //删除小节
            if (chapterIdList != null){
                List<String> videoSourceIdList = videoService.getAllByCourseId(id);
                videoService.deleteAllByChapterIdList(chapterIdList,id);
                // 批量删除小节视频
                if (!videoSourceIdList.isEmpty()){
                    String videoSourceIds = "";
                    for (String s : videoSourceIdList) {
                        videoSourceIds += (","+s);
                        videoSourceIds = videoSourceIds.substring(1);
                        vodClient.removeVideo(videoSourceIds);
                    }
                }
            }
            // 删除课程封面（调用OSS服务）
            R r = ossClient.deleteFile(fileName);
        }

        return R.ok();
    }
}

