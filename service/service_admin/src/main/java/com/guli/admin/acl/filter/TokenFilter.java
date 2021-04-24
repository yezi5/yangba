package com.guli.admin.acl.filter;

import com.guli.admin.acl.pojo.SecurityUser;
import com.guli.admin.acl.service.impl.UserDetailsServiceImpl;
import com.guli.commonutils.JwtUtils;
import com.guli.commonutils.MultiReadHttpServletRequest;
import com.guli.commonutils.MultiReadHttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 叶子
 * @Description 请设置
 * @PackageName com.guli.admin.acl.filter
 * @DevelopmentTools IntelliJ IDEA
 * @Data 2021/4/1 星期四 8:21
 */
@Component
public class TokenFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsServiceImpl service;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        MultiReadHttpServletRequest wrappedRequest = new MultiReadHttpServletRequest(request);
        MultiReadHttpServletResponse wrappedResponse = new MultiReadHttpServletResponse(response);

        String token = wrappedRequest.getHeader(JwtUtils.tokenName);
        if (!(token==null)){
            if (!JwtUtils.checkToken(token)){
                throw new AccessDeniedException("token已过期，请重新登录！");
            }
            SecurityUser securityUser = (SecurityUser) service.getUserByToken(token);
            if (securityUser == null || securityUser.getCurrentUserInfo() == null){
                throw new AccessDeniedException("token失效，请重新登录！");
            }

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(securityUser, null, securityUser.getAuthorities());
            // 全局注入角色权限信息和登录用户基本信息
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(wrappedRequest, wrappedResponse);
    }
}
