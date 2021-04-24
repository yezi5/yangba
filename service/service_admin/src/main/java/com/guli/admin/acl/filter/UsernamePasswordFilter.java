package com.guli.admin.acl.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.guli.commonutils.MultiReadHttpServletRequest;
import com.guli.commonutils.MultiReadHttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @author 叶子
 * @Description 请设置
 * @PackageName com.guli.admin.acl.filter
 * @DevelopmentTools IntelliJ IDEA
 * @Data 2021/4/1 星期四 9:30
 */
public class UsernamePasswordFilter extends UsernamePasswordAuthenticationFilter {

    public UsernamePasswordFilter(AuthenticationManager authenticationManager) {
        setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        UsernamePasswordAuthenticationToken authRequest = null;
        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }else {
            try {
                MultiReadHttpServletRequest wrapperRequest = new MultiReadHttpServletRequest(request);
                MultiReadHttpServletResponse wrapperResponse = new MultiReadHttpServletResponse(response);

                Map<String, String> map = new ObjectMapper().readValue(wrapperRequest.getInputStream(),Map.class);
                String username = map.get("username");
                String password = map.get("password");

                if (username == null) {
                    username = "";
                }
                if (password == null) {
                    password = "";
                }
                username = username.trim();
                authRequest = new UsernamePasswordAuthenticationToken(
                        username, password);

                // Allow subclasses to set the "details" property
                setDetails(request, authRequest);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return this.getAuthenticationManager().authenticate(authRequest);
    }
}
