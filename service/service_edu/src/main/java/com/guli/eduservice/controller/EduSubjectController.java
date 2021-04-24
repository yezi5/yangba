package com.guli.eduservice.controller;


import com.guli.commonutils.R;
import com.guli.eduservice.pojo.subject.OneSubject;
import com.guli.eduservice.service.EduSubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author 叶子
 * @since 2020-10-06
 */
@RestController
@RequestMapping("/eduservice/subject")
@CrossOrigin(allowCredentials = "true")
@Api(tags = "课程科目控制接口")
public class EduSubjectController {

    @Autowired
    private EduSubjectService service;

    @GetMapping("getAllSubject")
    @ApiOperation("获取所有一二级课程科目分类")
    public R getSubjects(){
        List<OneSubject> list = service.getOneAndTwoSubjects();

        return R.ok().data("list",list);
    }
}

