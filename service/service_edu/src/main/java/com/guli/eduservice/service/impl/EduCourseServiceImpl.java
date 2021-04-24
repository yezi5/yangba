package com.guli.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guli.eduservice.pojo.EduTeacher;
import com.guli.eduservice.pojo.course.CourseListVo;
import com.guli.eduservice.pojo.course.EduCourse;
import com.guli.eduservice.mapper.EduCourseMapper;
import com.guli.eduservice.pojo.front.CourseFrontVo;
import com.guli.eduservice.pojo.front.CourseWebVo;
import com.guli.eduservice.pojo.order.OrderDto;
import com.guli.eduservice.pojo.vo.CourseQuery;
import com.guli.eduservice.service.CourseViewCountService;
import com.guli.eduservice.service.EduCourseDescriptionService;
import com.guli.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guli.eduservice.service.EduTeacherService;
import com.guli.servicebase.annotation.Limit;
import com.guli.servicebase.util.RedisClient;
import com.guli.servicebase.util.RedisKey;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author 叶子
 * @since 2020-10-06
 */
@Service
@CacheConfig(cacheNames = "course")
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    private EduCourseDescriptionService descriptionService;
    @Autowired
    private EduTeacherService teacherService;
    @Autowired
    private CourseViewCountService viewCountService;
    @Autowired
    private RedisClient redis;


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

    @Override
    public Map<String, Object> getCourseFrontList(Page<EduCourse> pageParam, CourseFrontVo courseFrontVo) {
        //2 根据讲师id查询所讲课程
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("status","Normal");
        //判断条件值是否为空，不为空拼接
        if(!StringUtils.isEmpty(courseFrontVo.getSubjectParentId())) { //一级分类
            wrapper.eq("subject_parent_id",courseFrontVo.getSubjectParentId());
        }
        if(!StringUtils.isEmpty(courseFrontVo.getSubjectId())) { //二级分类
            wrapper.eq("subject_id",courseFrontVo.getSubjectId());
        }
        if(!StringUtils.isEmpty(courseFrontVo.getBuyCountSort())) { //关注度
            wrapper.orderByDesc("buy_count");
        }
        if (!StringUtils.isEmpty(courseFrontVo.getGmtCreateSort())) { //最新
            wrapper.orderByDesc("gmt_create");
        }

        if (!StringUtils.isEmpty(courseFrontVo.getPriceSort())) {//价格
            wrapper.orderByDesc("price");
        }

        baseMapper.selectPage(pageParam,wrapper);

        List<EduCourse> records = pageParam.getRecords();
        List<CourseListVo> courseListVoList = new LinkedList<>();
        records.forEach( eduCourse -> {
            CourseListVo courseListVo = new CourseListVo();
            BeanUtils.copyProperties(eduCourse,courseListVo);
            courseListVo.setBuyCount(String.valueOf(eduCourse.getBuyCount()));
            courseListVo.setViewCount(viewCountService.getCount(eduCourse.getId()));
            courseListVoList.add(courseListVo);
        });

        long current = pageParam.getCurrent();
        long pages = pageParam.getPages();
        long size = pageParam.getSize();
        long total = pageParam.getTotal();
        boolean hasNext = pageParam.hasNext();//下一页
        boolean hasPrevious = pageParam.hasPrevious();//上一页

        //把分页数据获取出来，放到map集合
        Map<String, Object> map = new HashMap<>();
        map.put("items", courseListVoList);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        //map返回
        return map;
    }

    @Override
    public CourseWebVo getBaseCourseInfo(String courseId) {
        CourseWebVo courseWebVo = null;
        // 基本信息获取
        courseWebVo = getBaseCourseInfoForPlayer(courseId);

        // 浏览量更新
        long incr = viewCountService.incr(courseId);
        courseWebVo.setViewCount(incr);

        return courseWebVo;
    }

    @Override
    public OrderDto getOrderDto(String courseId) {
        OrderDto orderDto = new OrderDto();

        EduCourse eduCourse = baseMapper.selectById(courseId);
        orderDto.setCourseId(courseId);
        orderDto.setCourseCover(eduCourse.getCover());
        orderDto.setCourseTitle(eduCourse.getTitle());
        orderDto.setTotalFee(eduCourse.getPrice());
        EduTeacher teacher = teacherService.getById(eduCourse.getTeacherId());
        orderDto.setTeacherName(teacher.getName());

        return orderDto;
    }

    @Override
    public List<CourseListVo> listIndex(int count) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("count",count);
        map.put("status","Normal");
        List<CourseListVo> courseList = baseMapper.selectList(map);

        return courseList;
    }

    @Override
    public List<CourseListVo> getCourseListVo(String teacherId) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("teacherId",teacherId);
        map.put("status","Normal");
        List<CourseListVo> courseList = baseMapper.getCourseListVo(map);

        return courseList;
    }

    @Override
    public CourseWebVo getBaseCourseInfoForPlayer(String courseId) {
        CourseWebVo courseWebVo = null;
        // 基本信息获取
        if (redis.hHasKey(RedisKey.MAP_KEY_COURSE_LIST,courseId)){
            courseWebVo = (CourseWebVo) redis.hget(RedisKey.MAP_KEY_COURSE_LIST,courseId);
        }else {
            courseWebVo = baseMapper.getBaseCourseInfo(courseId);
            redis.set("courseInfo::"+courseId,courseWebVo);
        }
        return courseWebVo;
    }
}
