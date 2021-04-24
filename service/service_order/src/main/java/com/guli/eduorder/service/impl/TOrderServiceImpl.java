package com.guli.eduorder.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.guli.commonutils.JwtUtils;
import com.guli.eduorder.client.EduClient;
import com.guli.eduorder.client.UcenterClient;
import com.guli.eduorder.pojo.vo.NativeVo;
import com.guli.eduorder.pojo.vo.OrderVo;
import com.guli.eduorder.pojo.TOrder;
import com.guli.eduorder.mapper.TOrderMapper;
import com.guli.eduorder.pojo.pay.OrderMsg;
import com.guli.eduorder.pojo.pay.OrderPara;
import com.guli.eduorder.service.TOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guli.eduorder.util.MathUtils;
import com.guli.eduorder.util.OkhttpClient;
import com.guli.eduorder.util.OrderNoUtil;
import com.guli.servicebase.handle.BadRequestException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author 叶子
 * @since 2020-10-21
 */
@Service
public class TOrderServiceImpl extends ServiceImpl<TOrderMapper, TOrder> implements TOrderService {

    @Autowired
    private EduClient eduClient;
    @Autowired
    private UcenterClient ucenterClient;
    @Autowired
    private OkhttpClient okhttpClient;

    @Override
    public String createOrder(String courseId, String memberId) {
        TOrder tOrder = new TOrder();
        String courseInfo = eduClient.getCourseInfo(courseId);
        Map<String,String> course = JSON.parseObject(courseInfo, Map.class);
        String memberInfo = ucenterClient.getMemberInfo(memberId);
        Map<String,String> member = JSON.parseObject(memberInfo, Map.class);

        tOrder.setCourseId(courseId);
        tOrder.setCourseTitle(course.get("courseTitle"));
        tOrder.setCourseCover(course.get("courseCover"));
        tOrder.setTeacherName(course.get("teacherName"));
        tOrder.setTotalFee(MathUtils.getBigDecimal(course.get("totalFee")));
        tOrder.setOrderNo(OrderNoUtil.getOrderNo());
        tOrder.setMemberId(member.get("id"));
        tOrder.setNickname(member.get("nickname"));
        tOrder.setMobile(member.get("mobile"));
        tOrder.setStatus(0);
        tOrder.setPayType(1);
        tOrder.setIsDeleted(false);

        int insert = baseMapper.insert(tOrder);
        if (insert <= 0){
            throw new BadRequestException(20001,"插入失败");
        }

        return tOrder.getId();
    }

    @Override
    public String createMyOrder(String courseId, String userId) {
        OrderPara orderPara = new OrderPara();

        String courseInfo = eduClient.getCourseInfo(courseId);
        Map<String,String> course = JSON.parseObject(courseInfo, Map.class);
        String memberInfo = ucenterClient.getMemberInfo(userId);
        Map<String,Object> member = JSON.parseObject(memberInfo, Map.class);

        BigDecimal bigDecimal = new BigDecimal(String.valueOf(course.get("totalFee")));
        bigDecimal = bigDecimal.setScale(2);

        Map<String,Object> map = new HashMap<>();
        map.put("price",bigDecimal.toString());
        map.put("name",course.get("courseTitle"));
        map.put("callbackurl","http://uwscas2u.dnat.tech/eduorder/paylog/callBack");
        map.put("thirduid",member.get("id"));
        OrderMsg orderMsg = createPayOrder(map);
        if (orderMsg!=null && "10001".equals(orderMsg.getCode()) ){
            TOrder tOrder = new TOrder();
            tOrder.setCourseId(courseId);
            tOrder.setCourseTitle(course.get("courseTitle"));
            tOrder.setCourseCover(course.get("courseCover"));
            tOrder.setTeacherName(course.get("teacherName"));
            tOrder.setTotalFee(MathUtils.getBigDecimal(course.get("totalFee")));
            // 注意这里要把   订单编号替换成站长付生成的订单ID号
            tOrder.setOrderNo(orderMsg.getOrderId());
            tOrder.setMemberId((String) member.get("id"));
            tOrder.setNickname((String) member.get("nickname"));
            tOrder.setMobile((String) member.get("mobile"));
            tOrder.setStatus(0);
            tOrder.setPayType(1);
            tOrder.setIsDeleted(false);
            tOrder.setZfbuseridcode(orderMsg.getZfbuseridcode());
            tOrder.setZfbcode(orderMsg.getZfbcode());
            tOrder.setWxcode(orderMsg.getWxcode());
            tOrder.setQrcode(orderMsg.getQrcode());

            int insert = baseMapper.insert(tOrder);
            if (insert <= 0){
                throw new BadRequestException(20001,"插入失败");
            }

            return tOrder.getId();
        }

        return null;
    }

    @Override
    public OrderMsg createPayOrder(Map<String,Object> para) {
        Map<String, String> headersMap = new HashMap<>();
        headersMap.put("Payment-Key","1e9f80af192a4cef");
        headersMap.put("Payment-Secret","167ffde0b3c7480fa9543ab6b26c466d");
        headersMap.put("Content-Type","application/x-www-form-urlencoded");
        String url = "https://admin.zhanzhangfu.com/order/createOrder";
        String json = JSONObject.toJSONString(para);

        OrderMsg result = null;

        try {
            result = JSONObject.toJavaObject(JSON.parseObject(okhttpClient.post(url,para,headersMap)),OrderMsg.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public OrderVo getOrderInfo(String orderId) {
        TOrder order = baseMapper.selectById(orderId);
        OrderVo orderVo = new OrderVo();
        if (order != null){
            BeanUtils.copyProperties(order,orderVo);
            return orderVo;
        }

        return null;
    }

    @Override
    public NativeVo createMyNative(String orderNo) {
        NativeVo nativeVo = new NativeVo();

        QueryWrapper<TOrder> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",orderNo);
        TOrder order = baseMapper.selectOne(wrapper);
        if (order != null){
            BeanUtils.copyProperties(order,nativeVo);
            return nativeVo;
        }
        return null;
    }

}
