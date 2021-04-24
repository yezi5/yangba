package com.guli.eduservice.controller;


import com.guli.commonutils.JwtUtils;
import com.guli.commonutils.R;
import com.guli.commonutils.ResultCode;
import com.guli.eduservice.pojo.vo.HistoryVo;
import com.guli.eduservice.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 浏览记录 前端控制器
 * </p>
 *
 * @author 叶子
 * @since 2021-04-07
 */
@RestController
@RequestMapping("/eduservice/history")
public class HistoryController {

    @Autowired
    private HistoryService historyService;

    @GetMapping("find")
    public R find(HttpServletRequest request){
        String userId = JwtUtils.getMemberIdByJwtToken(request);
        if (StringUtils.isEmpty(userId)){
            return R.error().code(ResultCode.NOT_LOGIN).message("请先登录");
        }
        List<HistoryVo> historyVoList = historyService.find8(userId);

        return R.ok().data("items",historyVoList);
    }
}

