package com.guli.admin.acl.controller;


import com.guli.admin.acl.pojo.User;
import com.guli.admin.acl.service.UserService;
import com.guli.commonutils.JwtUtils;
import com.guli.commonutils.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 叶子
 * @since 2021-03-31
 */
@RestController
@RequestMapping("/admin/user")
@CrossOrigin(allowCredentials = "true")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("info")
    @ApiOperation("获取用户信息")
    public R info(HttpServletRequest request) {
        String userId = JwtUtils.getMemberIdByJwtToken(request);
        if (StringUtils.isEmpty(userId)) return R.error();
        User user = userService.getById(userId);

        return R.ok().data("name",user.getUsername()).data("avatar",user.getAvatar());
    }
}

