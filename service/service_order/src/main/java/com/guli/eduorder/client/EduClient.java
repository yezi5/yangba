package com.guli.eduorder.client;

import com.guli.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient("service-edu")
public interface EduClient {

    @GetMapping("/eduservice/orderdto/getCourseInfo/{courseId}")
    String getCourseInfo(@PathVariable("courseId") String courseId);
}
