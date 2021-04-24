package com.guli.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.guli.eduservice.pojo.CourseViewCount;
import com.guli.eduservice.mapper.CourseViewCountMapper;
import com.guli.eduservice.service.CourseViewCountService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guli.servicebase.util.RedisClient;
import com.guli.servicebase.util.RedisKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 课程访问量 服务实现类
 * </p>
 *
 * @author 叶子
 * @since 2021-04-04
 */
@Service
public class CourseViewCountServiceImpl extends ServiceImpl<CourseViewCountMapper, CourseViewCount> implements CourseViewCountService {

    @Autowired
    private RedisClient redis;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public long incr(String courseId) {
        // 1. 缓存中有，更新缓存
        if (redis.hHasKey(RedisKey.MAP_KEY_VIEW_COUNT,courseId)){
            redis.hincr(RedisKey.MAP_KEY_VIEW_COUNT,courseId,1);
        }else {
            // 2. 缓存中没有，从数据库拿并更新缓存
            CourseViewCount courseViewCount = getOne(courseId);
            Long viewCount = 1L;
            if (courseViewCount != null){
                viewCount = Long.valueOf(courseViewCount.getViewCount());
            }
            redis.hset(RedisKey.MAP_KEY_VIEW_COUNT,courseId,viewCount+1);
        }
        return Long.parseLong(String.valueOf(redis.hget(RedisKey.MAP_KEY_VIEW_COUNT,courseId)));
    }

    @Override
    public String getCount(String courseId) {
        Object hget = redis.hget(RedisKey.MAP_KEY_VIEW_COUNT, courseId);
        // 缓存有，返回
        if (hget != null){
            return String.valueOf(hget);
        }
        // 缓存没有，查数据库
        CourseViewCount courseViewCount = getOne(courseId);
        if (courseViewCount == null){
            return "0";
        }
        return courseViewCount.getViewCount();
    }

    @Override
    public Map<String, String> getDataFromRedis() {
        if (!redis.hasKey(RedisKey.MAP_KEY_VIEW_COUNT)) return null;
        Map<String , String> map = new HashMap<>();
        Cursor cursor = redisTemplate.opsForHash().scan(RedisKey.MAP_KEY_VIEW_COUNT, ScanOptions.NONE);
        while (cursor.hasNext()){
            Map.Entry entry = (Map.Entry) cursor.next();

            String courseId = String.valueOf(entry.getKey());
            String viewCount = String.valueOf(entry.getValue());

            map.put(courseId,viewCount);
        }

        return map;
    }

    @Override
    @Scheduled(cron = "0 0 0/2 * * ? ")
    public void update() {
        Map<String, String> data = getDataFromRedis();
        if (data == null) return;

        data.keySet().forEach(key -> {
            String viewCount = data.get(key);

            QueryWrapper<CourseViewCount> wrapper = new QueryWrapper<>();
            wrapper.eq("course_id",key);
            CourseViewCount courseViewCount = baseMapper.selectOne(wrapper);
            if (courseViewCount != null){
                courseViewCount.setViewCount(viewCount);
                baseMapper.updateById(courseViewCount);
            }else {
                courseViewCount = new CourseViewCount();
                courseViewCount.setCourseId(key);
                courseViewCount.setViewCount(viewCount);
                baseMapper.insert(courseViewCount);
            }

        });
    }

    @Override
    public CourseViewCount getOne(String courseId) {
        QueryWrapper<CourseViewCount> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        CourseViewCount courseViewCount = baseMapper.selectOne(wrapper);

        return courseViewCount;
    }


}
