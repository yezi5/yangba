package com.guli.servicebase.util;

import org.springframework.stereotype.Component;

/**
 * @author 叶子
 * @Description 请设置
 * @PackageName com.guli.servicebase.util
 * @DevelopmentTools IntelliJ IDEA
 * @Data 2021/3/24 星期三 19:48
 */
@Component
public interface RedisKey {
    String TOKEN_KEY = "token";
    String STAR_KEY = "starList";
    String MAP_KEY_START_RECORD = "startMap";
    String SET_KEY_BAN = "blacklist";
    String MAP_KEY_VIEW_COUNT = "viewCount";
    // 课程详情MAP
    String MAP_KEY_COURSE_LIST = "courseList";
    // 博客收藏记录
    String MAP_KEY_BLOG_START = "blogStartList";
    // 博客收藏量
    String MAP_KEY_BLOG_START_COUNT = "blogStartCount";

    // 博客评论信息
    String MAP_KEY_BLOG_COMMENT_INFO = "blog::comment::info";

    // 博客评论热度排序
    String ZSET_KEY_BLOG_SORT_HEAT = "blog::comment::heat";

    // 博客时间排序
    String LIST_KEY_BLOG_SORT_TIME = "blog::comment::time";

    // 历史记录list
    String LIST_KEY_HISTORY = "historyList";

    // 历史记录map
    String MAP_KEY_HISTORY = "historyMap";
}
