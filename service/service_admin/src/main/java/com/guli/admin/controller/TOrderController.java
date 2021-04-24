package com.guli.admin.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guli.admin.pojo.TOrder;
import com.guli.admin.pojo.info.OrderQuery;
import com.guli.admin.service.TOrderService;
import com.guli.admin.service.TPayLogService;
import com.guli.commonutils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author 叶子
 * @since 2021-04-09
 */
@RestController
@RequestMapping("/admin/order")
public class TOrderController {

    @Autowired
    private TOrderService orderService;
    @Autowired
    private TPayLogService payLogService;


    @PostMapping("list/{page}/{limit}")
    public R list(@PathVariable Long page,
                  @PathVariable Long limit,
                  @RequestBody OrderQuery orderQuery){
        Map<String, Object> map = orderService.pageQuery(page,limit,orderQuery);

        return R.ok().data(map);
    }

    @GetMapping("rorder/{orderNo}")
    public R rOrder(@PathVariable String orderNo){
        boolean rs1 = orderService.updateStatus(orderNo);
        /*boolean rs2 = payLogService.replenishmentrder(orderNo);*/
        return R.ok();
    }

}

