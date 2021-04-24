package com.guli.admin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.guli.admin.pojo.EduChapter;
import com.guli.admin.pojo.EduVideo;
import com.guli.admin.pojo.vo.ChapterVo;
import com.guli.admin.service.EduChapterService;
import com.guli.admin.service.EduVideoService;
import com.guli.commonutils.R;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程章节 前端控制器
 * </p>
 *
 * @author 叶子
 * @since 2021-03-31
 */
@RestController
@RequestMapping("/admin/chapter")
@CrossOrigin(allowCredentials = "true")
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

    @ApiOperation(value = "添加章节")
    @PostMapping("addChapter")
    public R addChapter(@ApiParam(name = "章节对象",value = "章节对象",required = true)@RequestBody EduChapter chapter){
        service.save(chapter);

        return R.ok();
    }

    @ApiOperation(value = "修改章节信息")
    @PostMapping("updateChapter")
    public R updateChapter(@ApiParam(name = "章节对象",value = "章节对象",required = true) @RequestBody EduChapter chapter){
        service.updateById(chapter);

        return R.ok();
    }

    @ApiOperation(value = "删除章节信息")
    @DeleteMapping("{courseId}/{chapterId}")
    @Transactional
    public R deleteById(@ApiParam(name = "课程id",value = "课程主键",required = true) @PathVariable String courseId,
                        @ApiParam(name = "章节id",value = "章节主键",required = true) @PathVariable String chapterId){
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        wrapper.eq("chapter_id",chapterId);
        boolean a = videoService.remove(wrapper);
        boolean b = service.removeById(chapterId);
        if (a && b){
            return R.ok();
        }
        return R.error();
    }
}

