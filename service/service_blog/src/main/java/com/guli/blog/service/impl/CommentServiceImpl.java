package com.guli.blog.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guli.blog.client.UserClient;
import com.guli.blog.pojo.Comment;
import com.guli.blog.mapper.CommentMapper;
import com.guli.blog.pojo.MemberInfo;
import com.guli.blog.pojo.para.PublishCommentPara;
import com.guli.blog.pojo.vo.CommentChildVo;
import com.guli.blog.pojo.vo.CommentVo;
import com.guli.blog.service.CommentRedisService;
import com.guli.blog.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * <p>
 * 博客评论 服务实现类
 * </p>
 *
 * @author 叶子
 * @since 2021-04-05
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Resource
    private UserClient ucenterClient;

    @Override
    public boolean save(PublishCommentPara para, String userId) {
        Comment comment = new Comment();
        comment.setBlogId(para.getBlogId());
        comment.setCommonParentId(para.getTalked());
        comment.setContext(para.getTextarea());
        MemberInfo memberInfo = JSONObject.parseObject(ucenterClient.getMember(userId), MemberInfo.class);

        comment.setUserId(memberInfo.getId());
        comment.setAvatar(memberInfo.getAvatar());
        comment.setUsername(memberInfo.getNickname());
        comment.setLikeCount(0L);
        comment.setAverseCount(0L);

        return save(comment);
    }

    @Override
    public Map<String, Object> pageList(Long page, Long limit, String blogId) {
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        wrapper.eq("blog_id",blogId);
        wrapper.eq("common_parent_id","0");

        Page<Comment> pagePara = new Page<>(page, limit);
        IPage<Comment> iPage = page(pagePara, wrapper);

        List<Comment> records = iPage.getRecords();
        List<CommentVo> commentVoList = buildPage(records);
        long current = iPage.getCurrent();  // 当前页
        long pages = iPage.getPages();      // 总页数
        long total = iPage.getTotal();      // 总的数据量
        boolean hasNext = pagePara.hasNext();
        boolean hasPrevious = pagePara.hasPrevious();

        Map<String, Object> map = new HashMap<>();
        map.put("list",commentVoList);
        map.put("current",current);
        map.put("pages",pages);
        map.put("total",commentVoList.size());
        map.put("size",total);
        map.put("hasNext",hasNext);
        map.put("hasPrevious",hasPrevious);

        return map;
    }

    public List<CommentVo> buildPage(List<Comment> parentList){
        List<CommentVo> list = new ArrayList<>(parentList.size()+1);
        /*parentList.forEach(comment -> {
            CommentVo commentVo = new CommentVo();
            BeanUtils.copyProperties(comment,commentVo);
            List<CommentChildVo> childList = baseMapper.getChildList(comment.getId());
            List<CommentChildVo> childListInfo = new ArrayList<>(childList.size());
            childList.forEach(child -> {
                child.setCommonParentId(comment.getId());
                child.setCommonParentUserName(comment.getUsername());
                childListInfo.add(child);
            });
            commentVo.setChild(childList);
        });*/

        parentList.forEach(comment -> {
            CommentVo commentVo = new CommentVo();
            BeanUtils.copyProperties(comment,commentVo);
            // 第1级子评论
            List<CommentChildVo> childList = baseMapper.getChildList(comment.getId());
            // 子评论集
            List<CommentChildVo> childListInfo = new LinkedList<>();
            childList.forEach(child -> {
                if (child!=null){
                    child.setCommonParentId(comment.getId());
                    child.setCommonParentUserName(comment.getUsername());
                    childListInfo.add(child);
                    // 一级子评论树
                    childListInfo.addAll(addTree(child));
                }
            });

            commentVo.setChild(childListInfo);
            commentVo.setChildSize(childList.size());
            list.add(commentVo);
        });

        return list;
    }

    public List<CommentChildVo> addTree(CommentChildVo childVo){
        List<CommentChildVo> rsList = new LinkedList<>();
        // 二级子评论
        List<CommentChildVo> childList = baseMapper.getChildList(childVo.getId());
        if (!childList.isEmpty()){
            childList.forEach(childVo1 -> {
                if (childVo1!=null){
                    childVo1.setCommonParentId(childVo.getId());
                    childVo1.setCommonParentUserName(childVo.getUsername());
                    rsList.add(childVo1);
                    // >=2级子评论树
                    rsList.addAll(addTree(childVo1));
                }
            });
        }
        return rsList;
    }

    /*public List<CommentVo> buildPage(List<Comment> parentList, List<Comment> childList){
        List<CommentVo> commentVoList = new LinkedList<>();
        Map<String, List<CommentChildVo>> childMap = new HashMap<>();

        childList.forEach(comment -> {
            CommentChildVo commentChildVo = new CommentChildVo();
            BeanUtils.copyProperties(comment,commentChildVo);
            if (childMap.containsKey(comment.getCommonParentId())){
                List<CommentChildVo> commentChildVos = childMap.get(comment.getCommonParentId());
                commentChildVos.add(commentChildVo);
                childMap.replace(comment.getCommonParentId(),commentChildVos);
            }else {
                List<CommentChildVo> commentChildVos = new LinkedList<>();
                commentChildVos.add(commentChildVo);
                childMap.put(comment.getCommonParentId(),commentChildVos);
            }
        });

        parentList.forEach(comment -> {
            CommentVo commentVo = new CommentVo();
            BeanUtils.copyProperties(comment,commentVo);
            // 构建子评论
            List<CommentChildVo> list = new LinkedList<>();
            String id = commentVo.getId();
            String name = commentVo.getUsername();
            if (childMap.containsKey(id)){
                List<CommentChildVo> commentChildVoList = childMap.get(id);
                commentChildVoList.forEach(commentChildVo -> {
                    commentChildVo.setCommonParentId(id);
                    commentChildVo.setCommonParentUserName(name);
                    list.add(commentChildVo);
                });
                childMap.remove(id);
            }
            commentVo.setChild(list);
            commentVoList.add(commentVo);
        });

        return commentVoList;
    }

    public List<Comment> getAllChild(String blogId){
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        wrapper.eq("blog_id",blogId);
        wrapper.ne("common_parent_id","0");
        return list(wrapper);
    }*/
}
