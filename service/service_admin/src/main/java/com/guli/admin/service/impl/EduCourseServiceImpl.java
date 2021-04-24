package com.guli.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guli.admin.pojo.EduCourse;
import com.guli.admin.mapper.EduCourseMapper;
import com.guli.admin.pojo.EduCourseDescription;
import com.guli.admin.pojo.info.CourseInfoVo;
import com.guli.admin.pojo.info.CourseQuery;
import com.guli.admin.pojo.vo.CoursePublishVo;
import com.guli.admin.service.EduCourseDescriptionService;
import com.guli.admin.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guli.servicebase.handle.BadRequestException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author 叶子
 * @since 2021-03-30
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    private EduCourseDescriptionService descriptionService;

    /**
     * 分析：
     *      1.添加两张表信息：课程信息表+课程描述表
     *      2.两张表主键共用
     * 过程分析：
     *      1.创建课程对象，copy courseInfoVo属性，添加
     *      2.拿到课程id，创建课程描述对象，封装参数，添加
     * @param courseInfoVo 封装课程信息的课程ViewModel对象
     * @return
     */
    @Override
    public String saveCourse(CourseInfoVo courseInfoVo) {
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        int result = baseMapper.insert(eduCourse);
        if(result <= 0) {
            throw new BadRequestException(20001,"添加课程信息失败");
        }
        String cid = eduCourse.getId();
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setDescription(courseInfoVo.getDescription());
        eduCourseDescription.setId(cid);
        descriptionService.save(eduCourseDescription);

        return cid;
    }

    /**
     * 分析：
     *      1.涉及课程表+课程描述表
     *      2.课程描述表可用字段只有：description（课程描述）
     *      3.两张表主键共用
     *
     * 过程分析：
     *      1.根据课程主键查询 课程信息
     *      2.根据课程主键查询 课程描述信息
     *      3.创建课程ViewModel对象，封装参数，并返回ViewModel对象
     *
     * @param courseId 课程id
     * @return
     */
    @Override
    public CourseInfoVo getCourseInfo(String courseId) {
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        EduCourse eduCourse = baseMapper.selectById(courseId);
        EduCourseDescription description = descriptionService.getById(courseId);
        BeanUtils.copyProperties(eduCourse,courseInfoVo);
        courseInfoVo.setDescription(description.getDescription());

        return courseInfoVo;
    }

    /**
     * 注意：
     *      在添加课程信息时，拿到的课程对象没有id，添加后就有了（mp自动填充）
     *      在修改课程信息时，课程id由前端提供了，后端根据id修改课程信息
     * @param courseInfo 封装课程信息的课程ViewModel对象
     * @return
     */
    @Override
    public String updateCourseInfo(CourseInfoVo courseInfo) {
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfo,eduCourse);
        String courseId = eduCourse.getId();

        int update = baseMapper.updateById(eduCourse);

        EduCourseDescription description = new EduCourseDescription();
        description.setId(courseId);
        description.setDescription(courseInfo.getDescription());
        boolean update1 = descriptionService.updateById(description);
        if (update <= 0 || !update1){
            throw new BadRequestException(20001,"修改课程信息失败");
        }

        return courseId;
    }

    /**
     * 实现难点分析：
     *  需要查询三张表信息
     *
     *  对于这种复杂查询，最好手写SQL实现（效率相对较高）
     *
     * @param courseId 课程主键
     * @return
     */
    @Override
    public CoursePublishVo getPublishCourseInfo(String courseId) {
        CoursePublishVo publishCourseInfo = baseMapper.getPublishCourseInfo(courseId);
        return publishCourseInfo;
    }

    /**
     * 实现分析：
     *      根据ID修改课程状态即可
     *
     * @param id 课程id
     * @return
     */
    @Override
    public boolean publishCourse(String id) {
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(id);
        eduCourse.setStatus("Normal");
        int update = baseMapper.updateById(eduCourse);
        if (update <= 0){
            return false;
        }
        return true;
    }

    /**
     * 过程分析：
     *      1.封装查询条件：QueryWrapper
     *      2.使用selectPage方法查询课程信息（封装在传递的Page对象里）
     *
     * @param pagePara 分页参数
     * @param courseQuery 条件查询参数
     */
    @Override
    public void pageQuery(Page<EduCourse> pagePara, CourseQuery courseQuery) {
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();

        if (!StringUtils.isEmpty(courseQuery.getTitle())){
            wrapper.like("title",courseQuery.getTitle());
        }
        if (!StringUtils.isEmpty(courseQuery.getStatus())){
            wrapper.eq("status",courseQuery.getStatus());
        }
        baseMapper.selectPage(pagePara,wrapper);
    }
}
