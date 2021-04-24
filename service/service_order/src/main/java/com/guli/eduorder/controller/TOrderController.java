package com.guli.eduorder.controller;


import com.guli.commonutils.JwtUtils;
import com.guli.commonutils.R;
import com.guli.commonutils.ResultCode;
import com.guli.eduorder.pojo.vo.OrderVo;
import com.guli.eduorder.service.TOrderService;
import com.guli.servicebase.util.RedisClient;
import com.guli.servicebase.util.RedisKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author 叶子
 * @since 2020-10-21
 */
@RestController
@RequestMapping("/eduorder/order")
@CrossOrigin
public class TOrderController {

    @Autowired
    private TOrderService service;

    @Autowired
    private RedisClient redis;

    /**
     * 创建订单，基于微信API
     * @param map
     * @return
     */
    @PostMapping("createOrder")
    public R createOrder(@RequestBody Map<String,Object> map, HttpServletRequest request){
        String userId = JwtUtils.getMemberIdByJwtToken(request);
        if (!StringUtils.isEmpty(userId)){
            String token = (String) redis.get(RedisKey.TOKEN_KEY);
            String courseId = (String) map.get("courseId");
            String orderId = service.createOrder(courseId,JwtUtils.getMemberIdByJwtToken(token));

            return R.ok().data("orderId",orderId);
        }

        return R.error().code(ResultCode.NOT_LOGIN).message("请先登录");
    }

    /**
     * 创建订单，基于站长付API
     * @param map
     * @return
     */
    @PostMapping("createMyOrder")
    public R createMyOrder(@RequestBody Map<String,Object> map, HttpServletRequest request){
        String userId = JwtUtils.getMemberIdByJwtToken(request);
        if (!StringUtils.isEmpty(userId)){

            String courseId = (String) map.get("courseId");
            String orderId = service.createMyOrder(courseId,userId);
            if (StringUtils.isEmpty(orderId)){
                return R.error().message("创建订单失败");
            }
            return R.ok().data("orderId",orderId);
        }

        return R.error().code(ResultCode.NOT_LOGIN).message("请先登录");
    }

    /**
     * 获取订单信息
     * @param orderId
     * @return
     */
    @GetMapping("getOrderInfo/{orderId}")
    public R get(@PathVariable String orderId, HttpServletRequest request) {
        String userId = JwtUtils.getMemberIdByJwtToken(request);
        if (StringUtils.isEmpty(userId)){
            return R.error().code(ResultCode.NOT_LOGIN).message("请先登录");
        }
        OrderVo order = service.getOrderInfo(orderId);
        if (order == null){
            return R.error().message("不存在该订单");
        }
        return R.ok().data("item", order);
    }

}

