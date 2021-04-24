package com.guli.blog.service;

import com.guli.blog.pojo.Comment;

import java.util.List;
import java.util.Map;

/**
 * @author 叶子
 * @Description 请设置
 * @DevelopmentTools IntelliJ IDEA
 * @Data 2021/4/7 星期三 17:08
 */
public interface CommentRedisService {

    /**
     * 添加评论
     * @param comment
     * @return
     */
    boolean save(Comment comment);

    /**
     * 移除评论
     * @param key
     * @return
     */
    boolean remove(String key);

    /**
     * 点赞数+1
     * @param key
     * @return
     */
    long incrLikeCount(String key);

    /**
     * 点赞数-1
     * @param key
     * @return
     */
    long decrLikeCount(String key);

    /**
     * 点踩数+1
     * @param key
     * @return
     */
    long incrAverseCount(String key);

    /**
     * 点踩数-1
     * @param key
     * @return
     */
    long decrAverseCount(String key);

    /**
     * 根据指定key获取评论列表
     * @param keys
     * @return
     */
    List<Comment> getDataByKeys(List<String> keys);

    /**
     * 获取所有二级子评论
     * @param blogId
     * @return  Map<String, Comment>
     *     key      parentId
     *     value    Comment
     */
    Map<String, Comment> getChildComment(String blogId);

    /**
     * 从redis中获取所有评论信息
     * @return
     */
    List<Comment> getData();
}
