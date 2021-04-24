package com.guli.eduservice.service.impl;

import cn.hutool.core.util.IdUtil;
import com.guli.eduservice.pojo.History;
import com.guli.eduservice.mapper.HistoryMapper;
import com.guli.eduservice.pojo.course.EduCourse;
import com.guli.eduservice.pojo.vo.HistoryVo;
import com.guli.eduservice.service.EduCourseService;
import com.guli.eduservice.service.HistoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guli.servicebase.util.RedisClient;
import com.guli.servicebase.util.RedisKey;
import com.guli.servicebase.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * <p>
 * 浏览记录 服务实现类
 * </p>
 *
 * redis只存储新数据
 *
 * @author 叶子
 * @since 2021-04-07
 */
@Service
public class HistoryServiceImpl extends ServiceImpl<HistoryMapper, History> implements HistoryService {

    /**
     * list   id
     * hash
     *      id 对象
     *
     * 添加   redis
     * 删除
     *      1. redis有   移除
     *      2. redis没有  mysql
     * 查询
     */
    @Autowired
    private RedisClient redis;
    @Autowired
    private EduCourseService courseService;

    @Override
    @Transactional
    public boolean save(String courseId, String userId) {
        boolean rs = false;
        History history = new History(courseId,userId);
        return saveToRedis(history);
    }

    @Override
    public boolean saveToRedis(History history) {
        boolean rs = true;
        String listKey = RedisKeyUtil.key(RedisKey.LIST_KEY_HISTORY,history.getUserId());
        String mapKey = RedisKeyUtil.key(RedisKey.MAP_KEY_HISTORY,history.getUserId());

        redis.leftPush(listKey,history.getId());
        rs = redis.hset(mapKey,history.getId(),history);
        return rs;
    }

    @Override
    public boolean remove(String userId,String id) {
        boolean rs = true;
        String mapKey = RedisKeyUtil.key(RedisKey.MAP_KEY_HISTORY,userId);
        String listKey = RedisKeyUtil.key(RedisKey.LIST_KEY_HISTORY,userId);
        if (redis.hHasKey(mapKey,id)){
            redis.hdel(mapKey,id);
            // list数据移除
            redis.lRemove(listKey,1,id);
        }else {
            rs = removeById(id);
        }
        return rs;
    }

    @Override
    public List<HistoryVo> find8(String userId) {
        List<HistoryVo> historyVoList = new ArrayList<>(10);
        String mapKey = RedisKeyUtil.key(RedisKey.MAP_KEY_HISTORY,userId);

        String listKey = RedisKeyUtil.key(RedisKey.LIST_KEY_HISTORY,userId);
        if (redis.hasKey(mapKey)){
            List<Object> keyList = redis.lGet(listKey, 0, 8);
            keyList.forEach(key ->{
                History history = (History) redis.hget(mapKey, String.valueOf(key));
                HistoryVo historyVo = initHistoryVo(history);
                historyVoList.add(historyVo);
            });
        }else {
            // MySQL按时间排序查询8条最新浏览记录
                List<History> historyList = baseMapper.findNew(8);
                List<String> historyIds = new ArrayList<>(10);
                historyList.forEach(history -> {
                    HistoryVo historyVo = initHistoryVo(history);
                    historyVoList.add(historyVo);
                    // 将查询到的记录更新到缓存
                    saveToRedis(history);
                    historyIds.add(history.getId());
                });
            // 删除MySQL中查询得到的这几条记录
            removeByIds(historyIds);
        }

        return historyVoList;
    }

    private HistoryVo initHistoryVo(History history) {
        HistoryVo historyVo = new HistoryVo();
        EduCourse course = courseService.getById(history.getCourseId());
        String cover = course.getCover();
        String title = course.getTitle();
        historyVo.setId(history.getId());
        historyVo.setCover(cover);
        historyVo.setTitle(title);
        historyVo.setGmtCreate(history.getGmtCreate());
        return historyVo;
    }

    @Override
    public List<History> getDataFromRedis() {
        List<History> rs = new LinkedList<>();

        List<String> historyMapKeys = redis.getPrefixKeys(RedisKey.MAP_KEY_HISTORY);
        List<String> historyListKeys = redis.getPrefixKeys(RedisKey.LIST_KEY_HISTORY);
        historyMapKeys.forEach(historyMapKey ->{
            Map<Object, Object> historyMap = redis.hmget(historyMapKey);
            historyMap.keySet().forEach(key ->{
                History history = (History) historyMap.get(key);
                rs.add(history);
            });
        });
        // 清空缓存
        redis.del(historyListKeys);
        redis.del(historyMapKeys);

        return rs;
    }


    @Override
    @Scheduled(cron = "0 0 0/8 * * ? ")
    public void update() {
        List<History> historyList = getDataFromRedis();
        saveBatch(historyList);
    }
}
