package com.guli.eduorder.controller;

import cn.hutool.core.lang.UUID;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.guli.commonutils.JwtUtils;
import com.guli.commonutils.R;
import com.guli.commonutils.ResultCode;
import com.guli.eduorder.pojo.PayLog;
import com.guli.eduorder.pojo.TOrder;
import com.guli.eduorder.pojo.pay.NotifyPara;
import com.guli.eduorder.pojo.vo.NativeVo;
import com.guli.eduorder.service.PayLogService;
import com.guli.eduorder.service.TOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
@RestController
@RequestMapping("/eduorder/paylog")
@CrossOrigin
public class PayLogController {
    @Autowired
    private PayLogService payService;
    @Autowired
    private TOrderService orderService;
    /**
     * 生成二维码（微信API）
     *
     * @return
     */
    @GetMapping("/createNative/{orderNo}")
    public R createNative(@PathVariable String orderNo) {
        Map map = payService.createNative(orderNo);
        return R.ok().data(map);
    }

    /**
     * 基于站长付生成二维码（支付宝）
     * @param orderNo
     * @return
     */
    @GetMapping("/createMyNative/{orderNo}")
    public R createMyNative(@PathVariable String orderNo, HttpServletRequest request) {
        String userId = JwtUtils.getMemberIdByJwtToken(request);
        if (StringUtils.isEmpty(userId)){
            return R.error().code(ResultCode.NOT_LOGIN).message("请先登录");
        }
        NativeVo nativeVo = orderService.createMyNative(orderNo);
        return R.ok().data("native",nativeVo);
    }

    /**
     * 查询订单状态（微信API）
     * @param orderNo
     * @return
     */
    @GetMapping("/queryPayStatus/{orderNo}")
    public R queryPayStatus(@PathVariable String orderNo) {
        //调用查询接口
        Map<String, String> map = payService.queryPayStatus(orderNo);
        System.out.println(map);
        if (map == null) {//出错
            return R.error().message("支付出错");
        }
        if ("SUCCESS".equals(map.get("trade_state"))) {//如果成功
            //更改订单状态
            payService.updateOrderStatus(map);
            return R.ok().message("支付成功");
        }
        return R.ok().code(25000).message("支付中");
    }

    /**
     * 支付成功回调函数（站长付API）
     * @param notifyPara
     */
    @PostMapping("callBack")
    public void callBack(@RequestBody NotifyPara notifyPara){

        if ("10001".equals(notifyPara.getCode())){
            Map<String, String> map = new HashMap<>();
            map.put("out_trade_no",notifyPara.getOrderId());
            map.put("trade_state","SUCCESS");
            map.put("transaction_id", UUID.randomUUID().toString());
            map.put("paytype",notifyPara.getPaytype());
            //更改订单状态
            payService.updateOrderStatus(map);
        }
    }

    /**
     * 查询订单支付状态（基于站长付API）
     * @param map
     * @return
     */
    @PostMapping("/queryPayStatus")
    public R query(@RequestBody Map<String, Object> map){
        String orderNo = (String) map.get("orderNo");
        QueryWrapper<PayLog> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",orderNo);
        PayLog payLog = payService.getOne(wrapper);

        return payLog!=null?R.ok():R.error().code(25000);
    }
}