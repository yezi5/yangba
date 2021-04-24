package com.guli.ucenter.service;

import com.guli.ucenter.pojo.Member;
import com.baomidou.mybatisplus.extension.service.IService;
import com.guli.ucenter.pojo.vo.LoginVo;
import com.guli.ucenter.pojo.vo.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author 叶子
 * @since 2020-10-15
 */
public interface MemberService extends IService<Member> {

    String login(LoginVo loginVo);

    void register(RegisterVo registerVo);

    Member getOpenIdMember(String openid);

    //查询某一天注册人数
    Integer countRegisterDay(String day);
}
