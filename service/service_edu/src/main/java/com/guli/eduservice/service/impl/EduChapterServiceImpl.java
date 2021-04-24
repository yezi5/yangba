package com.guli.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.guli.eduservice.client.OrderClient;
import com.guli.eduservice.pojo.EduChapter;
import com.guli.eduservice.mapper.EduChapterMapper;
import com.guli.eduservice.pojo.EduVideo;
import com.guli.eduservice.pojo.vo.ChapterVo;
import com.guli.eduservice.pojo.vo.VideoVo;
import com.guli.eduservice.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guli.eduservice.service.EduCourseService;
import com.guli.eduservice.service.EduVideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author 叶子
 * @since 2020-10-10
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduVideoService videoService;
    @Autowired
    private EduCourseService courseService;
    @Autowired
    private OrderClient orderClient;

    /**
     * 分析:
     *      1.涉及章节表+小节表
     *      2.返回的章节（含小节）的ViewModel对象，需要二次封装
     *      3.课程主键+章节主键 --> 唯一确定 某课程某章节下的小节信息
     * 过程分析：
     *      1.根据课程id查询该课程下所有章节信息
     *      2.遍历章节，封装为ViewModel对象，同时根据（课程id+章节id）查询该章节下所有小节信息，
     *          并封装到ViewModel对象中
     * @param courseId 课程主键
     * @return
     */
    @Override
    public List<ChapterVo> getByCourseId(String courseId) {
        List<ChapterVo> list = new ArrayList<>();

        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        List<EduChapter> eduChapters = baseMapper.selectList(wrapper);

        for (EduChapter eduChapter : eduChapters) {
            ChapterVo chapterVo = new ChapterVo();
            chapterVo.setId(eduChapter.getId());
            chapterVo.setTitle(eduChapter.getTitle());

            QueryWrapper<EduVideo> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("course_id",courseId);
            queryWrapper.eq("chapter_id",chapterVo.getId());
            List<EduVideo> eduVideos = videoService.list(queryWrapper);
            List<VideoVo> videoVos = new ArrayList<>();
            for (EduVideo eduVideo : eduVideos) {
                VideoVo videoVo = new VideoVo();
                BeanUtils.copyProperties(eduVideo,videoVo);
                videoVos.add(videoVo);
            }
            chapterVo.setChildren(videoVos);
            list.add(chapterVo);
        }

        return list;
    }

    @Override
    public List<ChapterVo> getByCourseIdAndUserId(String courseId, String userId) {
        List<ChapterVo> list = new ArrayList<>();

        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        List<EduChapter> eduChapters = baseMapper.selectList(wrapper);

        Boolean isLogin = !StringUtils.isEmpty(userId);
        Boolean isOk = false;
        if (isLogin){
            isOk = canPlayer(courseId, userId);
        }

        for (EduChapter eduChapter : eduChapters) {
            ChapterVo chapterVo = new ChapterVo();
            chapterVo.setId(eduChapter.getId());
            chapterVo.setTitle(eduChapter.getTitle());

            QueryWrapper<EduVideo> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("course_id",courseId);
            queryWrapper.eq("chapter_id",chapterVo.getId());
            List<EduVideo> eduVideos = videoService.list(queryWrapper);
            List<VideoVo> videoVos = new ArrayList<>();
            for (EduVideo eduVideo : eduVideos) {
                VideoVo videoVo = new VideoVo();
                BeanUtils.copyProperties(eduVideo,videoVo);
                // 登陆了，根据课程的收费与否及用户是否已购买来判别
                if (isLogin && isOk){
                    videoVo.setIsFree(isOk);
                }
                // 没有登录，按照小节默认的收费状态
                videoVos.add(videoVo);
            }
            chapterVo.setChildren(videoVos);
            chapterVo.setChildrenSize(videoVos.size());
            list.add(chapterVo);
        }

        return list;
    }

    public Boolean canPlayer(String courseId, String userId){
        // 课程免费返回 true
        BigDecimal bigDecimal = new BigDecimal(0);
        int i = courseService.getById(courseId).getPrice().compareTo(bigDecimal);
        if (i <= 0){
            return true;
        }else { // 课程不免费
            // 如果用户购买了，就返回true
            return orderClient.getPayStatus(courseId, userId);
        }
    }

}
