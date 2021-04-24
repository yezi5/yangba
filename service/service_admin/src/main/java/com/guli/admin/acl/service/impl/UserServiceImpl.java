package com.guli.admin.acl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.guli.admin.acl.pojo.User;
import com.guli.admin.mapper.UserMapper;
import com.guli.admin.acl.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 叶子
 * @since 2021-03-31
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public User loadByUserName(String username) {
        User user = null;

        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username",username);
        user = baseMapper.selectOne(wrapper);

        return user;
    }
}
