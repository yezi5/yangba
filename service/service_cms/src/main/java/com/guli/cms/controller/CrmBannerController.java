package com.guli.cms.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.guli.cms.pojo.CrmBanner;
import com.guli.cms.service.CrmBannerService;
import com.guli.commonutils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.Cacheable;

import java.util.List;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author 叶子
 * @since 2020-10-15
 */
@RestController
@RequestMapping("/educms/bannerfront")
@CrossOrigin(allowCredentials = "true")
@Api(tags = "banner接口")
public class CrmBannerController {
    @Autowired
    private CrmBannerService service;

    @ApiOperation(value = "获取所有banner")
    @GetMapping("getAllBanner")
    public R getAllBanners(){
        List<CrmBanner> list = service.getIndexBanner();

        return R.ok().data("list",list);
    }
}

