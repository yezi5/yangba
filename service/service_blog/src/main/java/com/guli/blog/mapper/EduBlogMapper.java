package com.guli.blog.mapper;

import com.guli.blog.pojo.EduBlog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.guli.blog.pojo.para.UpdateBlogInfo;

import java.util.Map;

/**
 * <p>
 * 博客 Mapper 接口
 * </p>
 *
 * @author 叶子
 * @since 2021-03-24
 */
public interface EduBlogMapper extends BaseMapper<EduBlog> {


    int update(UpdateBlogInfo blogInfo);

    int updateStart(Map<String, Object> map);
}
