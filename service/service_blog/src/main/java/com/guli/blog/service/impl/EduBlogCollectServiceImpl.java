package com.guli.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.guli.blog.pojo.EduBlog;
import com.guli.blog.pojo.EduBlogCollect;
import com.guli.blog.mapper.EduBlogCollectMapper;
import com.guli.blog.pojo.vo.BlogListVo;
import com.guli.blog.service.BlogStartCountService;
import com.guli.blog.service.EduBlogCollectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guli.blog.service.EduBlogService;
import com.guli.commonutils.JwtUtils;
import com.guli.servicebase.util.RedisClient;
import com.guli.servicebase.util.RedisKey;
import com.guli.servicebase.util.RedisKeyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

/**
 * <p>
 * 博客收藏表 服务实现类
 * </p>
 *
 * @author 叶子
 * @since 2021-03-27
 */
@Service
public class EduBlogCollectServiceImpl extends ServiceImpl<EduBlogCollectMapper, EduBlogCollect> implements EduBlogCollectService {

    @Autowired
    private RedisClient redis;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private EduBlogService blogService;

    @Autowired
    private BlogStartCountService startCountService;

    @Override
    public boolean star(String blogId, String userId) {
        startCountService.incr(blogId);

        String key = RedisKeyUtil.key(userId, blogId);
        return redis.hset(RedisKey.MAP_KEY_BLOG_START, key, new Date());
    }

    @Override
    public boolean removeStar(String blogId, String userId) {
        startCountService.decr(blogId);
        boolean rs = true;
        String key = RedisKeyUtil.key(userId, blogId);
        if (redis.hHasKey(RedisKey.MAP_KEY_BLOG_START,key)){
            redis.hdel(RedisKey.MAP_KEY_BLOG_START,key);
        }else {
            rs = deleteByUserIdAndBlogId(userId,blogId);
        }
        return rs;
    }

    @Override
    public List<EduBlogCollect> getDataFromRedis() {
        if (!redis.hasKey(RedisKey.MAP_KEY_BLOG_START)) return null;
        List<EduBlogCollect> list = new LinkedList<>();
        Cursor scan = null;
        try {
            scan = redisTemplate.opsForHash().scan(RedisKey.MAP_KEY_BLOG_START, ScanOptions.NONE);
            while (scan.hasNext()){
                Map.Entry entry = (Map.Entry) scan.next();
                String key = String.valueOf(entry.getKey());
                String[] parseKey = RedisKeyUtil.parseKey(key);
                EduBlogCollect blogCollect = new EduBlogCollect();
                blogCollect.setMemberId(parseKey[0]);
                blogCollect.setBlogId(parseKey[1]);
                blogCollect.setGmtCreate((Date) entry.getValue());
                list.add(blogCollect);
                redis.hdel(RedisKey.MAP_KEY_BLOG_START,key);
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
        return list;
    }

    @Override
    @Scheduled(cron = "0 0 0/2 * * ? ")
    public void update(){
        List<EduBlogCollect> collectList = getDataFromRedis();
        if (collectList==null || collectList.isEmpty()) return;
        saveBatch(collectList);
    }

    @Override
    public List<BlogListVo> getStars(String userId) {
        update();
        List<BlogListVo> blogListVoList = new LinkedList<>();

        List<String> blogIdList = getBlogIds(userId);
        if (!blogIdList.isEmpty()){
            Collection<EduBlog> eduBlogs = blogService.listByIds(blogIdList);
            eduBlogs.forEach(eduBlog -> {
                BlogListVo blogListVo = new BlogListVo();
                BeanUtils.copyProperties(eduBlog,blogListVo);
                blogListVoList.add(blogListVo);
            });
        }

        return blogListVoList;
    }

    @Override
    public List<String> getBlogIds(String userId) {
        List<String> list = new LinkedList<>();
        QueryWrapper<EduBlogCollect> wrapper = new QueryWrapper<>();
        wrapper.eq("member_id",userId);
        List<EduBlogCollect> eduBlogCollects = baseMapper.selectList(wrapper);

        eduBlogCollects.forEach(eduBlogCollect -> list.add(eduBlogCollect.getBlogId()));

        return list;
    }

    @Override
    public boolean deleteByUserIdAndBlogId(String userId, String blogId){
        QueryWrapper<EduBlogCollect> wrapper = new QueryWrapper<>();
        wrapper.eq("member_id",userId);
        wrapper.eq("blog_id",blogId);
        return baseMapper.delete(wrapper) > 0;
    }
}
