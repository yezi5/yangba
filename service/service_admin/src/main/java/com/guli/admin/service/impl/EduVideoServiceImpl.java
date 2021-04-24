package com.guli.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.guli.admin.client.VodClient;
import com.guli.admin.pojo.EduVideo;
import com.guli.admin.mapper.EduVideoMapper;
import com.guli.admin.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mysql.jdbc.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author 叶子
 * @since 2021-03-30
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    @Autowired
    private VodClient vodClient;

    @Override
    public boolean deleteVideo(String id) {
        EduVideo eduVideo = baseMapper.selectById(id);
        String videoSourceId = eduVideo.getVideoSourceId();

        int i = baseMapper.deleteById(id);
        Map map = null;
        if (!StringUtils.isNullOrEmpty(videoSourceId)){
            vodClient.removeVideo(videoSourceId);
        }

        return  i > 0;
    }

    @Override
    public void deleteAllByChapterIdList(List<String> chapterIdList, String courseId) {
        QueryWrapper<EduVideo> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.eq("course_id",courseId);
        videoQueryWrapper.in("chapter_id",chapterIdList);
        baseMapper.delete(videoQueryWrapper);
    }

    @Override
    public List<String> getAllByCourseId(String courseId) {
        List<String> videoSourceIdList = new LinkedList<>();

        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        List<EduVideo> eduVideoList = baseMapper.selectList(wrapper);
        for (EduVideo eduVideo : eduVideoList) {
            videoSourceIdList.add(eduVideo.getVideoSourceId());
        }

        return videoSourceIdList;
    }

    @Override
    public String getCourseIdByVideoCourseId(String videoSourceId) {
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("video_source_id",videoSourceId);
        EduVideo video = getOne(wrapper);
        if (video != null){
            return video.getCourseId();
        }

        return null;
    }
}
