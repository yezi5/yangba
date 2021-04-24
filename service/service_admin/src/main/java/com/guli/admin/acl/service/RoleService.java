package com.guli.admin.acl.service;

import com.guli.admin.acl.pojo.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author 叶子
 * @since 2021-03-31
 */
public interface RoleService extends IService<Role> {

    /**
     * 根据用户ID获取角色名称列表
     * @param userId
     * @return
     */
    List<String> listRoleNameByUserId(String userId);
}
