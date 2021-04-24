package com.guli.eduservice.service;

import com.guli.eduservice.pojo.History;
import com.baomidou.mybatisplus.extension.service.IService;
import com.guli.eduservice.pojo.vo.HistoryVo;

import java.util.List;

/**
 * <p>
 * 浏览记录 服务类
 * </p>
 *
 * @author 叶子
 * @since 2021-04-07
 */
public interface HistoryService extends IService<History> {

    /**
     * 创建浏览记录，保存到redis
     * @param courseId
     * @param userId
     * @return
     */
    boolean save(String courseId, String userId);

    /**
     * 保存数据到redis
     * @param history
     * @return
     */
    boolean saveToRedis(History history);

    /**
     * 删除指定浏览记录，
     *  redis有  --> 新数据 --> 删除redis数据即可
     *  redis无  --> 旧数据 --> 删除MySQL数据
     * @param userId 用户id
     * @param id    浏览记录id
     * @return
     */
    boolean remove(String userId, String id);

    /**
     * 查找最新8条数据
     *  指定键存在   --> redis查询
     *  指定键不在   --> mysql查询并  ①缓存到redis ②删除MySQL查询出的记录
     *      ②主要是为了保证redis缓存数据的最新状态，避免部分数据假删除
     * @param userId
     * @return
     */
    List<HistoryVo> find8(String userId);

    // @TODO 数据列表的查询

    /**
     * 从redis中获取所有数据，同时清空缓存数据
     * @return
     */
    List<History> getDataFromRedis();

    /**
     * 缓存持久化到MySQL
     */
    void update();
}
