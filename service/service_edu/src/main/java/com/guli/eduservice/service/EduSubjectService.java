package com.guli.eduservice.service;

import com.guli.eduservice.pojo.subject.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.guli.eduservice.pojo.subject.OneSubject;
import com.guli.eduservice.pojo.subject.TwoSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author 叶子
 * @since 2020-10-06
 */
public interface EduSubjectService extends IService<EduSubject> {


    /**
     * 获取所有一二级分类
     * @return
     */
    List<OneSubject> getOneAndTwoSubjects();

    /**
     * 根据一级分类的id获取其下的所有二级分类对象
     * @param id
     * @return
     */
    List<TwoSubject> getTwoSubjects(String id);
}
