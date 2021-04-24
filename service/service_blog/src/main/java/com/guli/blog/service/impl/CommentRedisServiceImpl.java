package com.guli.blog.service.impl;

import com.guli.blog.pojo.Comment;
import com.guli.blog.service.CommentRedisService;
import com.guli.servicebase.util.RedisClient;
import com.guli.servicebase.util.RedisKey;
import com.guli.servicebase.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author 叶子
 * @Description 请设置
 * @DevelopmentTools IntelliJ IDEA
 * @Data 2021/4/7 星期三 17:08
 */
@Component
public class CommentRedisServiceImpl implements CommentRedisService {

    @Autowired
    private RedisClient redis;

    @Override
    @Transactional
    public boolean save(Comment comment) {
        boolean rs = true;
        String key = RedisKeyUtil.key(comment.getBlogId(),comment.getUserId());
        if (!redis.hHasKey(RedisKey.MAP_KEY_BLOG_COMMENT_INFO,key)){
            // 评论信息存储
            rs = redis.hset(RedisKey.MAP_KEY_BLOG_COMMENT_INFO,key,comment);
            // 评论时间排序存储
            String listKey = RedisKeyUtil.getKey(RedisKey.LIST_KEY_BLOG_SORT_TIME,comment.getBlogId());
            redis.leftPush(listKey,key);
            // 评论热度排序存储
            String setKey = RedisKeyUtil.getKey(RedisKey.ZSET_KEY_BLOG_SORT_HEAT,comment.getBlogId());
            redis.zset(setKey,key,Double.parseDouble(String.valueOf(comment.getLikeCount()-comment.getAverseCount())));
        }

        return rs;
    }

    @Override
    public boolean remove(String key) {
        return false;
    }

    @Override
    public long incrLikeCount(String key) {
        return 0;
    }

    @Override
    public long decrLikeCount(String key) {
        return 0;
    }

    @Override
    public long incrAverseCount(String key) {
        return 0;
    }

    @Override
    public long decrAverseCount(String key) {
        return 0;
    }

    @Override
    public List<Comment> getDataByKeys(List<String> keys) {
        return null;
    }

    @Override
    public Map<String, Comment> getChildComment(String blogId) {
        return null;
    }

    @Override
    public List<Comment> getData() {
        return null;
    }
}
