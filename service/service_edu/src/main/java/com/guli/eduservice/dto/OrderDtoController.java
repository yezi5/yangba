package com.guli.eduservice.dto;

import com.guli.eduservice.pojo.order.OrderDto;
import com.guli.eduservice.service.EduCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/eduservice/orderdto")
public class OrderDtoController {

    @Autowired
    private EduCourseService service;

    @GetMapping("getCourseInfo/{courseId}")
    public OrderDto getCourseInfo(@PathVariable String courseId){
        OrderDto orderDto = service.getOrderDto(courseId);
        return orderDto;
    }
}
