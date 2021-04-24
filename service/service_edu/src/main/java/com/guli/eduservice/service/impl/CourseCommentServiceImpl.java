package com.guli.eduservice.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guli.eduservice.client.UserClient;
import com.guli.eduservice.pojo.CourseComment;
import com.guli.eduservice.mapper.CourseCommentMapper;
import com.guli.eduservice.pojo.MemberInfo;
import com.guli.eduservice.pojo.para.PublishCommentPara;
import com.guli.eduservice.pojo.vo.CommentChildVo;
import com.guli.eduservice.pojo.vo.CommentVo;
import com.guli.eduservice.service.CourseCommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guli.eduservice.service.EduVideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * 课程评论 服务实现类
 * </p>
 *
 * @author 叶子
 * @since 2021-04-22
 */
@Service
public class CourseCommentServiceImpl extends ServiceImpl<CourseCommentMapper, CourseComment> implements CourseCommentService {

    @Autowired
    private UserClient userClient;
    @Autowired
    private EduVideoService videoService;

    @Override
    public boolean save(PublishCommentPara para, String userId) {
        CourseComment comment = new CourseComment();
        String courseId = videoService.getCourseIdByVideoCourseId(para.getBlogId());
        comment.setContext(para.getTextarea());
        comment.setCommonParentId(para.getTalked());
        comment.setCourseId(courseId);
        MemberInfo memberInfo = JSONObject.parseObject(userClient.getMember(userId), MemberInfo.class);

        comment.setUserId(memberInfo.getId());
        comment.setAvatar(memberInfo.getAvatar());
        comment.setUsername(memberInfo.getNickname());
        comment.setLikeCount(0L);
        comment.setAverseCount(0L);

        return save(comment);
    }

    @Override
    public Map<String, Object> pageList(Long page, Long limit, String videoSourceId) {
        QueryWrapper<CourseComment> wrapper = new QueryWrapper<>();
        String courseId = videoService.getCourseIdByVideoCourseId(videoSourceId);
        wrapper.eq("course_id",courseId);
        wrapper.eq("common_parent_id","0")
                .or()
                .eq("common_parent_id","");

        Page<CourseComment> pagePara = new Page<>(page, limit);
        IPage<CourseComment> iPage = page(pagePara, wrapper);

        List<CourseComment> records = iPage.getRecords();
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

    public List<CommentVo> buildPage(List<CourseComment> parentList){
        List<CommentVo> list = new ArrayList<>(parentList.size()+1);

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

}
