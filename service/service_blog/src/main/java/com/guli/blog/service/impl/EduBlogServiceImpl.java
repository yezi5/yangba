package com.guli.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guli.blog.pojo.EduBlog;
import com.guli.blog.mapper.EduBlogMapper;
import com.guli.blog.pojo.para.InsertBlogInfo;
import com.guli.blog.pojo.para.UpdateBlogInfo;
import com.guli.blog.pojo.vo.BlogListVo;
import com.guli.blog.pojo.vo.BlogUpdateVo;
import com.guli.blog.pojo.vo.BlogVo;
import com.guli.blog.service.EduBlogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guli.blog.service.UcenterService;
import com.guli.blog.util.MarkDownUtils;
import com.guli.blog.util.StringUtil;
import com.guli.commonutils.JwtUtils;
import com.guli.servicebase.util.RedisClient;
import com.guli.servicebase.util.RedisKey;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * <p>
 * 博客 服务实现类
 * </p>
 *
 * @author 叶子
 * @since 2021-03-24
 */
@Service
@CacheConfig(cacheNames = "blog")
public class EduBlogServiceImpl extends ServiceImpl<EduBlogMapper, EduBlog> implements EduBlogService {

    @Autowired
    private UcenterService ucenterService;

    @Autowired
    private RedisClient redis;

    @Override
    public boolean add(InsertBlogInfo blogInfo, String memberId) {
        EduBlog eduBlog = new EduBlog();
        String contextHtml = StringUtil.encode(blogInfo.getContextHtml());

        eduBlog.setTitle(blogInfo.getTitle());
        eduBlog.setBlogMemberId(memberId);
        eduBlog.setContextHtml(contextHtml);
        eduBlog.setContextMk(blogInfo.getContextMk());

        int insert = baseMapper.insert(eduBlog);

        return insert!=0;
    }

    @Override
    public Map<String,Object> pageQuery(Page<EduBlog> pageParam, String target) {
        QueryWrapper<EduBlog> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(target)){
            wrapper.like("title",target)
                    .or()
                    .like("context_mk",target);
        }
        wrapper.orderByDesc("gmt_create");
        baseMapper.selectPage(pageParam,wrapper);

        List<EduBlog> records = pageParam.getRecords();
        List<BlogListVo> list = new ArrayList<>(records.size());
        for (EduBlog record : records) {
            BlogListVo blogListVo = new BlogListVo();
            BeanUtils.copyProperties(record,blogListVo);
            String memberName = ucenterService.getMemberName(record.getBlogMemberId());
            blogListVo.setAuthor(memberName);
            list.add(blogListVo);
        }

        long current = pageParam.getCurrent();
        long pages = pageParam.getPages();
        long size = pageParam.getSize();
        long total = list.size();
        boolean hasNext = pageParam.hasNext();//下一页
        boolean hasPrevious = pageParam.hasPrevious();//上一页

        //把分页数据获取出来，放到map集合
        Map<String, Object> map = new HashMap<>();
        map.put("items", list);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        //map返回
        return map;
    }



    @Override
    @CacheEvict(key = "#blogInfo.blogId", condition = "#result == true ")
    public boolean update(UpdateBlogInfo blogInfo) {
        int count = baseMapper.update(blogInfo);

        return count > 0;
    }

    @Override
    @Cacheable(key = "#blogId")
    public BlogVo getBlog(String blogId) {
        EduBlog eduBlog = baseMapper.selectById(blogId);
        BlogVo blogVo = new BlogVo();
        if (eduBlog!=null){
            BeanUtils.copyProperties(eduBlog,blogVo);
        }

        return blogVo;
    }

    @Override
    @CacheEvict(key = "#blogId", condition = "#result == true ")
    public boolean remove(String blogId) {
        int count = 0;
        try {
            count = baseMapper.deleteById(blogId);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

        return count > 0;
    }

    @Override
    public HttpServletResponse download(String blogId, HttpServletResponse response) {
        EduBlog blog = getById(blogId);
        String contextMK = blog.getContextMk();
        String fileName = blog.getTitle();
        try {
            File file = MarkDownUtils.createNewFile(contextMK, fileName);
            response = MarkDownUtils.download(response, file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }

    @Override
    public List<BlogListVo> getMyBlog() {
        if (!redis.hasKey(RedisKey.TOKEN_KEY)) return null;

        List<BlogListVo> voList = new LinkedList<>();
        String token = (String) redis.get(RedisKey.TOKEN_KEY);
        String userId = JwtUtils.getMemberIdByJwtToken(token);
        QueryWrapper<EduBlog> wrapper = new QueryWrapper<>();
        wrapper.eq("blog_member_id",userId);
        List<EduBlog> eduBlogList = baseMapper.selectList(wrapper);

        eduBlogList.forEach(eduBlog -> {
            BlogListVo blogListVo = new BlogListVo();
            BeanUtils.copyProperties(eduBlog,blogListVo);
            voList.add(blogListVo);
        });

        return voList;
    }

    @Override
    public BlogUpdateVo getBlogUpdateVo(String blogId) {
        BlogUpdateVo blogUpdateVo = null;
        EduBlog eduBlog = baseMapper.selectById(blogId);
        if (eduBlog != null){
            blogUpdateVo = new BlogUpdateVo();
            BeanUtils.copyProperties(eduBlog,blogUpdateVo);
        }

        return blogUpdateVo;
    }

    @Override
    public boolean updateStart(Map<String, Object> map) {
        return baseMapper.updateStart(map)>0;
    }

}
