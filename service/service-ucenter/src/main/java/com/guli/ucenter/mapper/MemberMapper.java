package com.guli.ucenter.mapper;

import com.guli.ucenter.pojo.Member;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author 叶子
 * @since 2020-10-15
 */
public interface MemberMapper extends BaseMapper<Member> {

    //查询某一天注册人数
    Integer countRegisterDay(String day);
}
