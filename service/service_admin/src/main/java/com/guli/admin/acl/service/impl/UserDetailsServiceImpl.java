package com.guli.admin.acl.service.impl;

import com.guli.admin.acl.pojo.SecurityUser;
import com.guli.admin.acl.pojo.User;
import com.guli.admin.acl.service.RoleService;
import com.guli.admin.acl.service.UserRoleService;
import com.guli.admin.acl.service.UserService;
import com.guli.commonutils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 叶子
 * @Description 请设置
 * @PackageName com.guli.admin.acl.service.impl
 * @DevelopmentTools IntelliJ IDEA
 * @Data 2021/4/1 星期四 8:21
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.loadByUserName(username);
        if (user == null){
            throw new UsernameNotFoundException("没有该用户信息");
        }
        List<String> roleNameList = roleService.listRoleNameByUserId(user.getUserId());

        SecurityUser securityUser = new SecurityUser();
        securityUser.setCurrentUserInfo(user);
        securityUser.setPermissionValueList(roleNameList);

        return securityUser;
    }

    public UserDetails getUserByToken(String token) {
        String username = JwtUtils.getUserNameByJwtToken(token);
        UserDetails userDetails = loadUserByUsername(username);

        return userDetails;
    }
}
