package com.guli.admin.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guli.admin.pojo.EduTeacher;
import com.guli.admin.pojo.info.TeacherQuery;
import com.guli.admin.service.EduTeacherService;
import com.guli.commonutils.R;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author 叶子
 * @since 2021-03-30
 */
@RestController
@RequestMapping("/admin/teacher")
@CrossOrigin(allowCredentials = "true")
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
     * 根据id删除教师（逻辑删除）
     * 只会返回true
     * @param id
     * @return
     */
    @DeleteMapping("{id}")
    @ApiOperation("根据id删除教师（逻辑删除）")
    public R delete(@ApiParam(name = "教师id",value = "教师主键",required = true) @PathVariable String id){
        boolean remove = service.removeById(id);
        if (!remove){
            return R.error();
        }
        return R.ok();
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
     * 添加教师信息
     * @param teacher
     * @return
     */
    @PostMapping("addTeacher")
    @ApiOperation("添加教师信息")
    public R add(@ApiParam(name = "教师对象",value = "教师对象",required = true)@RequestBody EduTeacher teacher){


        boolean save = service.save(teacher);
        if (save){
            return R.ok();
        }

        return R.error();
    }

    /**
     * 根据教师id查询教师
     * @param id
     * @return
     */
    @GetMapping("getTeacher/{id}")
    @ApiOperation("根据教师id查询教师")
    public R findById(@ApiParam(name = "教师id",value = "教师主键",required = true)@PathVariable String id){
        EduTeacher teacher = service.getById(id);

        if (teacher != null){
            return R.ok().data("teacher",teacher);
        }
        return R.error();
    }

    /**
     * 根据教师id更新教师信息
     * @param teacher
     * @return
     */
    @PostMapping("updateTeacher")
    @ApiOperation("根据教师id更新教师信息")
    public R updateById(@ApiParam(name = "教师对象",value = "教师对象",required = true) @RequestBody(required = false) EduTeacher teacher){

        boolean update = service.updateById(teacher);
        if (update){
            return R.ok();
        }

        return R.error();
    }
}

