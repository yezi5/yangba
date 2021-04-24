package com.guli.admin.service;

import com.guli.admin.pojo.TOrder;
import com.baomidou.mybatisplus.extension.service.IService;
import com.guli.admin.pojo.info.OrderQuery;

import java.util.Map;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author 叶子
 * @since 2021-04-09
 */
public interface TOrderService extends IService<TOrder> {

    /**
     * 分页查询+条件查询
     * @param page
     * @param limit
     * @param orderQuery
     * @return
     */
    Map<String, Object> pageQuery(Long page, Long limit, OrderQuery orderQuery);

    /**
     * 根据订单号修改订单状态
     * @param orderNo
     * @return
     */
    boolean updateStatus(String orderNo);
}
