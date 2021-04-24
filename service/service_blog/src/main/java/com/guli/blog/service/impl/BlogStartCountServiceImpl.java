package com.guli.blog.service.impl;

import com.guli.blog.pojo.EduBlog;
import com.guli.blog.service.BlogStartCountService;
import com.guli.blog.service.EduBlogService;
import com.guli.servicebase.util.RedisClient;
import com.guli.servicebase.util.RedisKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 叶子
 * @Description 请设置
 * @DevelopmentTools IntelliJ IDEA
 * @Data 2021/4/5 星期一 13:54
 */
@Service
public class BlogStartCountServiceImpl implements BlogStartCountService {

    @Autowired
    private RedisClient redis;

    @Autowired
    private EduBlogService blogService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Long incr(String blogId) {
        if (redis.hHasKey(RedisKey.MAP_KEY_BLOG_START_COUNT,blogId)){
            redis.hincr(RedisKey.MAP_KEY_BLOG_START_COUNT,blogId,1);
        }else {
            EduBlog blog = blogService.getById(blogId);
            redis.hset(RedisKey.MAP_KEY_BLOG_START_COUNT,blogId,blog.getBlogStar()+1);
        }
        return getCount(blogId);
    }

    @Override
    public Long decr(String blogId) {
        if (redis.hHasKey(RedisKey.MAP_KEY_BLOG_START_COUNT,blogId)){
            redis.hdecr(RedisKey.MAP_KEY_BLOG_START_COUNT,blogId,1);
        }else {
            EduBlog blog = blogService.getById(blogId);
            redis.hset(RedisKey.MAP_KEY_BLOG_START_COUNT,blogId,blog.getBlogStar()-1);
        }
        return getCount(blogId);
    }

    @Override
    public Long getCount(String blogId) {
        long rs = 0L;
        if (redis.hHasKey(RedisKey.MAP_KEY_BLOG_START_COUNT,blogId)){
            rs = Long.parseLong(String.valueOf(redis.hget(RedisKey.MAP_KEY_BLOG_START_COUNT,blogId)));
        }else {
            EduBlog blog = blogService.getById(blogId);
            rs = blog.getBlogStar();
        }

        return rs;
    }

    @Override
    @Scheduled(cron = "0 0 0/2 * * ? ")
    public void update() {
        Cursor scan = null;
        if (redis.hasKey(RedisKey.MAP_KEY_BLOG_START_COUNT)){
            try {
                scan = redisTemplate.opsForHash().scan(RedisKey.MAP_KEY_BLOG_START_COUNT, ScanOptions.NONE);
                while (scan.hasNext()){
                    Map.Entry entry = (Map.Entry) scan.next();
                    String blogId = String.valueOf(entry.getKey());
                    Long startCount = Long.parseLong(String.valueOf(entry.getValue()));
                    Map<String, Object> map = new HashMap<>();
                    map.put("blogId",blogId);
                    map.put("startCount",startCount);
                    blogService.updateStart(map);
                }
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                if (scan!=null){
                    try {
                        scan.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
