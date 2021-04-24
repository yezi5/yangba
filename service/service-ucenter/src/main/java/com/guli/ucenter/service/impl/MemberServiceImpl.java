package com.guli.ucenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.guli.commonutils.JwtUtils;
import com.guli.servicebase.handle.BadRequestException;
import com.guli.servicebase.util.RedisClient;
import com.guli.servicebase.util.RedisKey;
import com.guli.ucenter.pojo.Member;
import com.guli.ucenter.mapper.MemberMapper;
import com.guli.ucenter.pojo.vo.LoginVo;
import com.guli.ucenter.pojo.vo.RegisterVo;
import com.guli.ucenter.service.MemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guli.ucenter.util.MD5;
import com.mysql.jdbc.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author 叶子
 * @since 2020-10-15
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Autowired
    private RedisClient redisClient;

    @Override
    public String login(LoginVo loginVo) {
        if (loginVo == null){
            throw new BadRequestException(20001,"登陆出错");
        }
        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();


        Member member = baseMapper.selectOne(new QueryWrapper<Member>().eq("mobile", mobile));
        if (member == null){
            throw new BadRequestException(20001,"此账号没有注册");
        }

        if (member.getIsDeleted()){
            throw new BadRequestException(20001,"此账号已注销");
        }

        if (!member.getPassword().equals(MD5.encrypt(password))){
            throw new BadRequestException(20001,"密码错误");
        }

        //使用JWT生成token字符串
        String token = JwtUtils.getJwtToken(member.getId(), member.getNickname());
        // 缓存token信息，有效时长 24h
        redisClient.set(RedisKey.TOKEN_KEY,token,60*60*24);

        return token;
    }

    @Override
    public void register(RegisterVo registerVo) {
        String nickname = registerVo.getNickname();
        String mobile = registerVo.getMobile();
        String password = registerVo.getPassword();
        String code = registerVo.getCode();

        if (StringUtils.isNullOrEmpty(nickname) ||
            StringUtils.isNullOrEmpty(mobile) ||
            StringUtils.isNullOrEmpty(password) ||
            StringUtils.isNullOrEmpty(code)){
            throw new BadRequestException(20001,"数据不能为空");
        }

        if (redisTemplate.hasKey(mobile)){
            if (!code.equals(redisTemplate.opsForValue().get(mobile))){
                throw new BadRequestException(20001,"验证码错误");
            }
        }else {
            throw new BadRequestException(20001,"请点击发送验证码");
        }

        Integer count = baseMapper.selectCount(new QueryWrapper<Member>().eq("mobile", mobile));
        if (count > 0){
            throw new BadRequestException(20001,"该手机号已注册");
        }

        Member member = new Member();
        BeanUtils.copyProperties(registerVo,member);
        member.setIsDeleted(false);
        member.setIsDisabled(false);
        member.setPassword(MD5.encrypt(password));
        member.setAvatar("https://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLJHTX1IwEl1CEQgPlPhLIynEqkiaMKibgic44Lo2xKrytgTfgt2OevjiaxGzic46MYStJwgd43dkN2ibYA/132");
        baseMapper.insert(member);
    }

    //根据openid判断
    @Override
    public Member getOpenIdMember(String openid) {
        QueryWrapper<Member> wrapper = new QueryWrapper<>();
        wrapper.eq("openid",openid);
        Member member = baseMapper.selectOne(wrapper);
        return member;
    }


    @Override
    public Integer countRegisterDay(String day) {
        return baseMapper.countRegisterDay(day);
    }
}
