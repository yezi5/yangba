package com.guli.eduorder.dto;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.guli.eduorder.pojo.PayLog;
import com.guli.eduorder.pojo.TOrder;
import com.guli.eduorder.service.PayLogService;
import com.guli.eduorder.service.TOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 叶子
 * @Description 请设置
 * @PackageName com.guli.eduorder.dto
 * @DevelopmentTools IntelliJ IDEA
 * @Data 2021/3/21 星期日 20:12
 */
@RestController
@RequestMapping("/eduorder/orderdto")
@CrossOrigin
public class OrderDto {

    @Autowired
    private TOrderService orderService;
    @Autowired
    private PayLogService payLogService;

    @GetMapping("getPayStatus/{courseId}/{memberId}")
    public Boolean getPayStatus(@PathVariable String courseId,
                          @PathVariable String memberId){

        QueryWrapper<TOrder> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        wrapper.eq("member_id",memberId);
        wrapper.eq("status",1);
        TOrder order = orderService.getOne(wrapper);

        return order!=null;
    }
}
