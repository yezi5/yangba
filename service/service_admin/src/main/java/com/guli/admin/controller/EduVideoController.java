package com.guli.admin.controller;


import com.guli.admin.pojo.EduVideo;
import com.guli.admin.service.EduVideoService;
import com.guli.commonutils.R;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author 叶子
 * @since 2021-03-30
 */
@RestController
@RequestMapping("/admin/video")
@CrossOrigin(allowCredentials = "true")
public class EduVideoController {

    @Autowired
    private EduVideoService eduVideoService;


    @ApiOperation(value = "添加小节")
    @PostMapping("/addVideo")
    public R addVideo(@ApiParam(name = "小节对象",value = "小节对象",required = true) @RequestBody EduVideo eduVideo) {
        this.eduVideoService.save(eduVideo);
        return R.ok();
    }

    @ApiOperation(value = "根据小节id查询")
    @GetMapping("/getVideoInfo/{id}")
    public R getVideoInfo(@ApiParam(name = "小节id",value = "小节主键",required = true) @PathVariable String id) {
        EduVideo eduVideo = this.eduVideoService.getById(id);
        return R.ok().data("eduVideo",eduVideo);
    }

    @ApiOperation(value = "修改小节")
    @PostMapping("/updateVideo")
    public R updateVideo(@ApiParam(name = "小节对象",value = "小节对象",required = true) @RequestBody EduVideo eduVideo) {
        this.eduVideoService.updateById(eduVideo);
        return R.ok();
    }

    @ApiOperation(value = "删除小节")
    @DeleteMapping("{id}")
    public R deleteVideo(@ApiParam(name = "小节id",value = "小节主键",required = true) @PathVariable String id){

        boolean isOk = eduVideoService.deleteVideo(id);
        if (isOk){
            return R.ok();
        }
        return R.error();
    }
}

