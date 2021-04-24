package com.guli.admin.mapper;

import com.guli.admin.pojo.TOrder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 订单 Mapper 接口
 * </p>
 *
 * @author 叶子
 * @since 2021-04-09
 */
public interface TOrderMapper extends BaseMapper<TOrder> {

    int updateStatus(String orderNo);
}
