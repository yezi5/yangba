package com.guli.admin.controller;


import com.guli.admin.pojo.excel.OneSubject;
import com.guli.admin.service.EduSubjectService;
import com.guli.commonutils.R;
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
 * @since 2021-03-30
 */
@RestController
@RequestMapping("/admin/subject")
@CrossOrigin(allowCredentials = "true")
public class EduSubjectController {

    @Autowired
    private EduSubjectService service;

    @PostMapping("addSubject")
    @ApiOperation("添加一二级课程科目分类")
    public R addSubjects(@ApiParam(name = "文件对象",value = "上传文件对象",required = true) MultipartFile file){

        boolean save = service.save(file, service);
        if (save){
            return R.ok();
        }else {
            return R.error();
        }
    }

    @GetMapping("getAllSubject")
    @ApiOperation("获取所有一二级课程科目分类")
    public R getSubjects(){
        List<OneSubject> list = service.getOneAndTwoSubjects();

        return R.ok().data("list",list);
    }
}

