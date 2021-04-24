package com.guli.eduorder.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.guli.eduorder.pojo.PayLog;

import java.util.Map;

public interface PayLogService {

    public Map createNative(String orderNo);

    void updateOrderStatus(Map<String, String> map);

    Map queryPayStatus(String orderNo);

    PayLog getOne(QueryWrapper<PayLog> wrapper);
}
