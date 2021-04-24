package com.guli.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.guli.commonutils.JwtUtils;
import com.guli.eduservice.pojo.course.EduCourse;
import com.guli.eduservice.pojo.course.EduCourseCollect;
import com.guli.eduservice.mapper.EduCourseCollectMapper;
import com.guli.eduservice.pojo.vo.CollectCourseListVo;
import com.guli.eduservice.service.EduCourseCollectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guli.eduservice.service.EduCourseService;
import com.guli.servicebase.util.RedisClient;
import com.guli.servicebase.util.RedisKey;
import com.guli.servicebase.util.RedisStartUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * 课程收藏 服务实现类
 * </p>
 *
 * 基于Redis缓存优化
 * 创建一个set集合
 *  key：RedisKey.STAR_KEY+"::"+userId
 *  valus：courseId
 *
 *  缓存策略;
 *      1. 添加，直接操作缓存
 *      2. 删除，缓存有就操作缓存，没有就操作数据库
 *      3. 查询状态，缓存有就返回true，没有就查询数据库
 *      4. 定时持久化并清空缓存
 *  缓存永远保存的是新数据
 *  缓存有就意味着是新数据，直接操作缓存即可
 *  没有就是旧数据，查数据库
 *
 * @author 叶子
 * @since 2020-10-06
 */
@Service
public class EduCourseCollectServiceImpl extends ServiceImpl<EduCourseCollectMapper, EduCourseCollect> implements EduCourseCollectService {

    @Autowired
    private RedisClient redis;
    @Autowired
    private EduCourseService courseService;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public boolean star(String courseId, String token) {
        String userId = JwtUtils.getMemberIdByJwtToken(token);

        String key = RedisStartUtil.getKey(userId,courseId);
        return redis.hset(RedisKey.MAP_KEY_START_RECORD, key, new Date());
    }

    @Override
    public boolean removeByCourseId(String courseId, String token) {
        String userId = JwtUtils.getMemberIdByJwtToken(token);

        String key = RedisStartUtil.getKey(userId, courseId);
        boolean rs = redis.hHasKey(RedisKey.MAP_KEY_START_RECORD,key);
        if (rs){
            redis.hdel(RedisKey.MAP_KEY_START_RECORD,key);
        }else {
            QueryWrapper<EduCourseCollect> wrapper = new QueryWrapper<>();
            wrapper.eq("member_id",userId);
            wrapper.eq("course_id",courseId);
            rs = remove(wrapper);
        }
        return rs;
    }

    @Override
    public List<CollectCourseListVo> getStarListByToken(String token) {
        //缓存持久化
        update();
        List<CollectCourseListVo> list = new LinkedList<>();
        String userId = JwtUtils.getMemberIdByJwtToken(token);

        List<String> courseIds = baseMapper.selectCourseIds(userId);
        if (!courseIds.isEmpty()){
            Collection<EduCourse> eduCourses = courseService.listByIds(courseIds);
            eduCourses.forEach(eduCourse -> {
                CollectCourseListVo courseListVo = new CollectCourseListVo();
                BeanUtils.copyProperties(eduCourse,courseListVo);
                courseListVo.setCourseId(eduCourse.getId());
                list.add(courseListVo);
            });
        }
        return list;
    }

    @Override
    public boolean isStar(String courseId, String userId) {
        boolean rs = redis.hHasKey(RedisKey.MAP_KEY_START_RECORD,RedisStartUtil.getKey(userId,courseId));

        return rs?true:getOne(userId,courseId)!=null;
    }

    /**
     * 从redis中获取所有收藏记录，并清空缓存
     * @return
     */
    public List<EduCourseCollect> getDataFromRedis(){
        if (!redis.hasKey(RedisKey.MAP_KEY_START_RECORD)) return null;
        Cursor cursor = redisTemplate.opsForHash().scan(RedisKey.MAP_KEY_START_RECORD, ScanOptions.NONE);
        List<EduCourseCollect> list = new LinkedList<>();

        while (cursor.hasNext()){
            Map.Entry entry = (Map.Entry) cursor.next();
            String key = (String) entry.getKey();
            String[] parseKey = RedisStartUtil.parseKey(key);
            Date date = (Date) entry.getValue();
            EduCourseCollect courseCollect = new EduCourseCollect();
            courseCollect.setMemberId(parseKey[0]);
            courseCollect.setCourseId(parseKey[1]);
            courseCollect.setGmtCreate(date);
            list.add(courseCollect);
            redis.hdel(RedisKey.MAP_KEY_START_RECORD,key);
        }
        return list;
    }

    @Override
    @Scheduled(cron = "0 0 0/2 * * ? ")
    public void update() {
        List<EduCourseCollect> list = getDataFromRedis();
        if (list != null){
            list.forEach(eduCourseCollect -> save(eduCourseCollect));
        }
    }

    public EduCourseCollect getOne(String userId, String courseId){
        QueryWrapper<EduCourseCollect> wrapper = new QueryWrapper<>();
        wrapper.eq("member_id",userId);
        wrapper.eq("course_id",courseId);
        return getOne(wrapper);
    }

}
