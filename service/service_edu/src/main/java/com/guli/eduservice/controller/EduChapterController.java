package com.guli.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.guli.commonutils.R;
import com.guli.eduservice.pojo.EduChapter;
import com.guli.eduservice.pojo.EduVideo;
import com.guli.eduservice.pojo.vo.ChapterVo;
import com.guli.eduservice.service.EduChapterService;
import com.guli.eduservice.service.EduVideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author 叶子
 * @since 2020-10-10
 */
@RestController
@RequestMapping("/eduservice/chapter")
@CrossOrigin(allowCredentials = "true")
@Api(tags = "章节处理器")
public class EduChapterController {

    @Autowired
    private EduChapterService service;
    @Autowired
    private EduVideoService videoService;

    @ApiOperation(value = "获取课程下所有章节（包括小节）")
    @GetMapping("getChapterVideo/{courseId}")
    public R getAllChapterByCourseId(@ApiParam(name = "课程id",value = "课程主键",required = true) @PathVariable String courseId){

        List<ChapterVo> chapterVideoList = service.getByCourseId(courseId);

        return R.ok().data("allChapterVideo",chapterVideoList);
    }

    @ApiOperation(value = "获取章节信息")
    @GetMapping("getChapterInfo/{chapterId}")
    public R getChapterInfo(@ApiParam(name = "章节id",value = "章节主键",required = true)@PathVariable String chapterId){
        EduChapter chapter = service.getById(chapterId);

        return R.ok().data("chapter",chapter);
    }


}

