package com.guli.admin.acl.handel;

import com.guli.commonutils.R;
import com.guli.commonutils.ResponseUtil;
import com.guli.commonutils.ResultCode;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 叶子
 * @Description 认证失败处理器
 * @PackageName com.yezi.office.acl.handel
 * @DevelopmentTools IntelliJ IDEA
 * @Data 2021/1/4 星期一 11:16
 */
@Component
public class UserAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        ResponseUtil.out(httpServletResponse, R.error().code(ResultCode.NOT_LOGIN).message("登陆失败"));
    }
}
