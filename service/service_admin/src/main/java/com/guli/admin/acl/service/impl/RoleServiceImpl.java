package com.guli.admin.acl.service.impl;

import com.guli.admin.acl.pojo.Role;
import com.guli.admin.mapper.RoleMapper;
import com.guli.admin.acl.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author 叶子
 * @since 2021-03-31
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Override
    public List<String> listRoleNameByUserId(String userId) {
        List<String> roleNameList = null;

        roleNameList = baseMapper.listRoleNameByUserId(userId);

        return roleNameList;
    }
}
