package com.guli.blog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guli.blog.pojo.EduBlog;
import com.baomidou.mybatisplus.extension.service.IService;
import com.guli.blog.pojo.para.InsertBlogInfo;
import com.guli.blog.pojo.para.UpdateBlogInfo;
import com.guli.blog.pojo.vo.BlogListVo;
import com.guli.blog.pojo.vo.BlogUpdateVo;
import com.guli.blog.pojo.vo.BlogVo;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 博客 服务类
 * </p>
 *
 * @author 叶子
 * @since 2021-03-24
 */
public interface EduBlogService extends IService<EduBlog> {

    /**
     * 写博客
     * @param blogInfo 博客信息
     * @param memberId 用户id
     * @return 添加成功返回true 否则返回 false
     */
    boolean add(InsertBlogInfo blogInfo, String memberId);

    /**
     * 分页查询+条件查询
     *
     * @param pagePara
     * @param target
     * @return
     */
    Map<String, Object> pageQuery(Page<EduBlog> pagePara, String target);

    /**
     * 修改博客信息
     * @param blogInfo
     * @return
     */
    boolean update(UpdateBlogInfo blogInfo);

    /**
     * 根据博客id获取博客信息
     * @param blogId
     * @return
     */
    BlogVo getBlog(String blogId);

    /**
     * 根据博客id删除博客
     * @param blogId
     * @return
     */
    boolean remove(String blogId);

    HttpServletResponse download(String blogId, HttpServletResponse response);

    /**
     * 用户用户个人博客
     *
     * @return
     */
    List<BlogListVo> getMyBlog();

    /**
     * 获取要修改博客的信息
     * @param blogId
     * @return
     */
    BlogUpdateVo getBlogUpdateVo(String blogId);

    /**
     * 根据博客id修改博客收藏量
     * @param map
     */
    boolean updateStart(Map<String, Object> map);
}
