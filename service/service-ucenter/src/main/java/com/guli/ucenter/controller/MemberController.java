package com.guli.ucenter.controller;


import com.guli.commonutils.JwtUtils;
import com.guli.commonutils.R;
import com.guli.servicebase.util.RedisClient;
import com.guli.servicebase.util.RedisKey;
import com.guli.ucenter.pojo.Member;
import com.guli.commonutils.ordervo.UcenterMemberOrder;
import com.guli.ucenter.pojo.vo.LoginVo;
import com.guli.ucenter.pojo.vo.RegisterVo;
import com.guli.ucenter.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author 叶子
 * @since 2020-10-15
 */
@RestController
@RequestMapping("/ucenter/member")
@CrossOrigin(allowCredentials = "true")
@Api(tags = "会员登录与注册接口")
public class MemberController {

    @Autowired
    private MemberService service;
    @Autowired
    private RedisClient redis;


    // @TODO 单用户token不行

    @ApiOperation(value = "登录")
    @PostMapping("login")
    public R login(@ApiParam(name = "登录VM对象",value = "封装登录用户参数的VM对象",required = true) @RequestBody LoginVo loginVo){
        String token = service.login(loginVo);
        redis.set(RedisKey.TOKEN_KEY,token);
        return R.ok().data("token",token);
    }

    @ApiOperation(value = "注册")
    @PostMapping("register")
    public R register(@ApiParam(name = "注册VM对象",value = "封装注册用户参数的VM对象",required = true) @RequestBody RegisterVo registerVo){
        service.register(registerVo);

        return R.ok();
    }

    @ApiOperation(value = "获取用户信息")
    @GetMapping("getMemberInfo")
    public R getLoginInfo(@ApiParam(name = "HttpServletRequest对象",value = "HttpServletRequest对象",required = false) HttpServletRequest request){
        try {
            String memberId = JwtUtils.getMemberIdByJwtToken(request);
            Member member = service.getById(memberId);
            return R.ok().data("userInfo",member);
        }catch (Exception e){
            e.printStackTrace();
            return R.error();
        }
    }

    @GetMapping("logout")
    public R logout(){
        redis.del(RedisKey.TOKEN_KEY);
        return R.ok();
    }

    //查询某一天注册人数
    @GetMapping("countRegister/{day}")
    public R countRegister(@PathVariable String day) {
        Integer count = service.countRegisterDay(day);
        return R.ok().data("countRegister",count);
    }
}

