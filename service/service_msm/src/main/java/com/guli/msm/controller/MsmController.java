package com.guli.msm.controller;


import com.guli.commonutils.R;
import com.guli.msm.service.MsmService;
import com.guli.msm.util.RandomUtil;
import com.mysql.jdbc.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/edumsm/msm")
@CrossOrigin(allowCredentials = "true")
@Api(tags = "短信业务接口")
public class MsmController {
    @Autowired
    private MsmService service;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @ApiOperation(value = "发送验证码")
    @GetMapping("send/{phone}")
    public R sendCode(@ApiParam(name = "手机号",value = "接收验证码的手机号",required = true) @PathVariable String phone){
        if (StringUtils.isNullOrEmpty(phone)){
            return R.error().message("手机号为空");
        }

        String code = RandomUtil.getFourBitRandom();
        Map<String,Object> param = new HashMap<>();
        param.put("code",code);

        boolean isOk = service.sendCode(phone,param);
        if (isOk){
            //key不存在
            if (!redisTemplate.hasKey(phone)){
                redisTemplate.opsForValue().set(phone,code,5, TimeUnit.MINUTES);
            }else {
                redisTemplate.delete(phone);
                redisTemplate.opsForValue().set(phone,code,5, TimeUnit.MINUTES);
            }
            return R.ok();
        }


        return R.error();
    }
}
