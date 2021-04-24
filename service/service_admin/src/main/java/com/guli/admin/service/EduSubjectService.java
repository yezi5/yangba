package com.guli.admin.service;

import com.guli.admin.pojo.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.guli.admin.pojo.excel.OneSubject;
import com.guli.admin.pojo.excel.TwoSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author 叶子
 * @since 2021-03-30
 */
public interface EduSubjectService extends IService<EduSubject> {

    /**
     * 解析上传的excel文件，获取一二级课程分类并加入数据库
     * @param file      上传的excel文件
     * @param service   EduSubjectService对象，excel监听器使用
     * @return true-->加入成功  false-->加入失败
     */
    boolean save(MultipartFile file, EduSubjectService service);

    /**
     * 查询目标一级分类是否存在
     * @param subjectName    一级分类名
     * @return  存在返回true，不存在返回false
     */
    boolean existOneSubject(String subjectName);

    /**
     * 查询目标二级分类是否存在
     * @param subjectName    二级分类名
     * @return  存在返回true，不存在返回false
     */
    Boolean existTwoSubject(String subjectName);

    /**
     * 根据分类名获取id
     * @param subjectName   分类名
     * @return  有该分类则返回true，否则返回false
     */
    String getSubjectId(String subjectName);

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
