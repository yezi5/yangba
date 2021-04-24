package com.guli.admin.acl.handel;


import com.alibaba.fastjson.JSONObject;
import com.guli.admin.acl.pojo.SecurityUser;
import com.guli.admin.acl.pojo.User;
import com.guli.admin.acl.service.UserService;
import com.guli.commonutils.JwtUtils;
import com.guli.commonutils.R;
import com.guli.commonutils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author 叶子
 * @Description 认证成功处理器
 * @PackageName com.yezi.office.acl.handel
 * @DevelopmentTools IntelliJ IDEA
 * @Data 2021/1/4 星期一 11:14
 */
@Component
public class UserAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        SecurityUser securityUser = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = securityUser.getCurrentUserInfo();
        String token = JwtUtils.getJwtToken(user.getUserId(), user.getUsername());
        securityUser.setCurrentUserInfo(user);
        ResponseUtil.out(httpServletResponse, R.ok().message("登陆成功").data("token",token));
    }
}
