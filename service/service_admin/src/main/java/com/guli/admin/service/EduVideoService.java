package com.guli.admin.service;

import com.guli.admin.pojo.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author 叶子
 * @since 2021-03-30
 */
public interface EduVideoService extends IService<EduVideo> {

    /**
     * 删除小节信息
     * @param id
     * @return
     */
    boolean deleteVideo(String id);

    /**
     * 删除某课程下所有小节
     * @param chapterIdList 章节主键list集合
     * @param courseId 课程主键
     */
    void deleteAllByChapterIdList(List<String> chapterIdList, String courseId);

    /**
     * 查询某课程下所有小节的视频资源id
     * @param courseId
     * @return
     */
    List<String> getAllByCourseId(String courseId);

    /**
     * 根据视频资源id获取课程id
     * @param videoSourceId
     * @return
     */
    String getCourseIdByVideoCourseId(String videoSourceId);
}
