package com.guli.eduservice.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.guli.commonutils.R;
import com.guli.eduservice.client.VodClient;
import com.guli.eduservice.pojo.EduVideo;
import com.guli.eduservice.mapper.EduVideoMapper;
import com.guli.eduservice.pojo.course.EduCourse;
import com.guli.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mysql.jdbc.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springfox.documentation.spring.web.json.Json;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author 叶子
 * @since 2020-10-10
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {


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
