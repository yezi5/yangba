package com.guli.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guli.admin.pojo.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;
import com.guli.admin.pojo.info.TeacherQuery;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author 叶子
 * @since 2021-03-30
 */
public interface EduTeacherService extends IService<EduTeacher> {

    /**
     * 分页查询+条件查询
     * @param pagePara 分页查询参数
     * @param teacherQuery 条件查询参数
     */
    void pageQuery(Page<EduTeacher> pagePara, TeacherQuery teacherQuery);
}
