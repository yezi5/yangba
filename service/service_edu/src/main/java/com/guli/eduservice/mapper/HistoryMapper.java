package com.guli.eduservice.mapper;

import com.guli.eduservice.pojo.History;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 浏览记录 Mapper 接口
 * </p>
 *
 * @author 叶子
 * @since 2021-04-07
 */
public interface HistoryMapper extends BaseMapper<History> {

    List<History> findNew(int limit);
}
