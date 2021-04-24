package com.guli.eduservice.service;

import com.guli.eduservice.pojo.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author 叶子
 * @since 2020-10-10
 */
public interface EduVideoService extends IService<EduVideo> {

    /**
     * 根据视频资源id获取课程id
     * @param videoSourceId
     * @return
     */
    String getCourseIdByVideoCourseId(String videoSourceId);
}
