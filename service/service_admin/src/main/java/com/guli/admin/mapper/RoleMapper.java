package com.guli.admin.mapper;

import com.guli.admin.acl.pojo.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 *
 * @author 叶子
 * @since 2021-03-31
 */
public interface RoleMapper extends BaseMapper<Role> {

    List<String> listRoleNameByUserId(String userId);
}
