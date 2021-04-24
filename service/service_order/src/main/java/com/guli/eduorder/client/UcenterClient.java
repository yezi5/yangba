package com.guli.eduorder.client;

import com.guli.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Component
@FeignClient("service-ucenter")
public interface UcenterClient {

    @GetMapping("/ucenter/orderDto/getMember/{memberId}")
    String getMemberInfo(@PathVariable("memberId") String memberId);
}
