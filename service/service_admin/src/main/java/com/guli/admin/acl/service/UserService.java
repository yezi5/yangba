package com.guli.admin.acl.service;

import com.guli.admin.acl.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 叶子
 * @since 2021-03-31
 */
public interface UserService extends IService<User> {

    /**
     * 根据用户名查找用户信息
     * @param username
     * @return
     */
    User loadByUserName(String username);
}
