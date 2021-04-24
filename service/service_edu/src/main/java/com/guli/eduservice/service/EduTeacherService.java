package com.guli.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guli.eduservice.pojo.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;
import com.guli.eduservice.pojo.course.EduCourse;
import com.guli.eduservice.pojo.front.CourseFrontVo;
import com.guli.eduservice.pojo.vo.TeacherQuery;

import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author 叶子
 * @since 2020-09-29
 */
public interface EduTeacherService extends IService<EduTeacher> {

    /**
     * 分页查询+条件查询
     * @param pagePara 分页查询参数
     * @param teacherQuery 条件查询参数
     */
    void pageQuery(Page<EduTeacher> pagePara, TeacherQuery teacherQuery);

    //1 分页查询讲师的方法
    Map<String, Object> getTeacherFrontList(Page<EduTeacher> pageTeacher);
}
