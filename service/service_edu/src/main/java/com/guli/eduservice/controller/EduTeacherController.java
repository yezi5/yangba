package com.guli.eduservice.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guli.commonutils.R;
import com.guli.eduservice.pojo.EduTeacher;
import com.guli.eduservice.pojo.vo.TeacherQuery;
import com.guli.eduservice.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author 叶子
 * @since 2020-09-29
 */
@RestController
@RequestMapping("/eduservice/teacher")
@CrossOrigin(allowCredentials = "true")
@Api(tags = "教师操作接口")
public class EduTeacherController {

    @Autowired
    private EduTeacherService service;

    /**
     * 查询教师列表
     * @return
     */
    @GetMapping("findAll")
    @ApiOperation("查询教师列表")
    public R findAll(){
        List<EduTeacher> list = service.list(null);
        return R.ok().data("items", list);
    }

    /**
     * 分页查询
     * @param page  当前页
     * @param limit 每页显示数据量
     * @return
     */
    @PostMapping("findAll/{page}/{limit}")
    @ApiOperation("分页查询")
    public R findPage(@ApiParam(name = "当前页",value = "当前所在页面数",required = true) @PathVariable Long page,
                      @ApiParam(name = "每页数据量",value = "每页显示数据量",required = true) @PathVariable Long limit){

        Page<EduTeacher> teacherPage = new Page<>(page,limit);
        service.page(teacherPage,null);
        List<EduTeacher> records = teacherPage.getRecords();
        long total = teacherPage.getTotal();

        return R.ok().data("total",total).data("records",records);
    }

    /**
     * 条件查询+分页查询
     * 注意：
     *  @RequestBody注解 使用json格式传递数据，将前端数据封装为对应对象
     *  使用这个注解后请求方式需为post，不能是get
     *  使用RestFul风格时不能够在路径中写@RequestBody注解标注的属性
     *
     * @param page
     * @param limit
     * @param teacherQuery
     * @return
     */
    @PostMapping("pageTeacherCondition/{page}/{limit}")
    @ApiOperation("条件查询+分页查询")
    public R select(@ApiParam(name = "当前页数",value = "当前所在页数",required = true)@PathVariable Long page,
                    @ApiParam(name = "每页数据量",value = "每页数据量",required = true)@PathVariable Long limit,
                    @ApiParam(name = "封装的查询条件对象",value = "查询条件",required = false)@RequestBody(required = false) TeacherQuery teacherQuery){

        Page<EduTeacher> pagePara = new Page<>(page,limit);

        service.pageQuery(pagePara,teacherQuery);
        List<EduTeacher> records = pagePara.getRecords();
        long total = pagePara.getTotal();
        System.out.println(total);

        return R.ok().data("total",total).data("rows",records);
    }

    /**
     * 根据教师id查询教师
     * @param id
     * @return
     */
    @GetMapping("getTeacher/{id}")
    @ApiOperation("根据教师id查询教师")
    @Cacheable(cacheNames = "teacher", key = "#id" )
    public R findById(@ApiParam(name = "教师id",value = "教师主键",required = true)@PathVariable String id){
        EduTeacher teacher = service.getById(id);

        if (teacher != null){
            return R.ok().data("teacher",teacher);
        }
        return R.error();
    }

}

