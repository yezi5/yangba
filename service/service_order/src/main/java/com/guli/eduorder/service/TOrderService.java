package com.guli.eduorder.service;

import com.guli.eduorder.pojo.vo.NativeVo;
import com.guli.eduorder.pojo.vo.OrderVo;
import com.guli.eduorder.pojo.pay.OrderMsg;
import com.guli.eduorder.pojo.TOrder;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author 叶子
 * @since 2020-10-21
 */
public interface TOrderService extends IService<TOrder> {


    /**
     * 微信API接入使用的订单
     * @param courseId
     * @param memberId
     * @return
     */
    String createOrder(String courseId, String memberId);


    /**
     * 站长付接入使用的订单
     * @param courseId
     * @param userId
     * @return
     */
    String createMyOrder(String courseId, String userId);

    /**
     * 创建站长付订单
     * @param para
     * @return
     */
    OrderMsg createPayOrder(Map<String,Object> para);

    /**
     * 获取订单信息
     * @param orderId
     * @return
     */
    OrderVo getOrderInfo(String orderId);

    /**
     * 创建二维码
     * @param orderNo
     * @return
     */
    NativeVo createMyNative(String orderNo);
}
