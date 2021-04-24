package com.guli.admin.acl.handel;

import com.guli.commonutils.R;
import com.guli.commonutils.ResponseUtil;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 叶子
 * @Description 未登录认证处理器
 * @PackageName com.yezi.office.acl.handel
 * @DevelopmentTools IntelliJ IDEA
 * @Data 2021/1/4 星期一 10:48
 */
@Component
public class UserAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        ResponseUtil.out(httpServletResponse, R.error().message("未登录"));
    }
}


