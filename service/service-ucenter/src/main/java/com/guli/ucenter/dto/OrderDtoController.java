package com.guli.ucenter.dto;

import com.alibaba.fastjson.JSON;
import com.guli.ucenter.pojo.Member;
import com.guli.ucenter.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/ucenter/orderDto")
public class OrderDtoController {

    @Autowired
    private MemberService service;

    @GetMapping("getMember/{memberId}")
    public Member getMember(@PathVariable String memberId){
        Member member = service.getById(memberId);

        return member;
    }
}
