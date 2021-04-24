package com.guli.servicebase.aop;

import com.guli.commonutils.*;
import com.guli.servicebase.annotation.Limit;
import com.guli.servicebase.handle.BadRequestException;
import com.guli.servicebase.util.IpUtils;
import com.guli.servicebase.util.LimitType;
import com.guli.servicebase.util.RedisClient;
import com.guli.servicebase.util.RedisKey;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @author 叶子
 * @Description 请设置
 * @DevelopmentTools IntelliJ IDEA
 * @Data 2021/4/4 星期日 11:16
 */
@Aspect
@Component
public class LimitAOP {

    @Autowired
    private RedisClient redis;

    @Pointcut("@annotation(com.guli.servicebase.annotation.Limit)")
    public void pointCut(){

    }

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest httpServletRequest = attributes.getRequest();

        MultiReadHttpServletRequest request = new MultiReadHttpServletRequest(httpServletRequest);

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method signatureMethod = signature.getMethod();
        Limit limit = signatureMethod.getAnnotation(Limit.class);
        int period = limit.period();
        int count = limit.count();
        long banTime = limit.banTime();
        String key = limit.key();
        // 默认使用IP限制，指定key的情况下不使用IP
        if (StringUtils.isEmpty(key)){
            key = IpUtils.getIpAddr(request);
        }

        if (redis.hasKey(key)){
            Integer c = Integer.parseInt(String.valueOf(redis.get(key)));
            if (c <= 0){
                redis.sSetAndTime(RedisKey.SET_KEY_BAN,banTime,key);
                throw new BadRequestException();
            }else {
                redis.decr(key,1);
            }
        }else {
            redis.set(key,count,period);
        }

        return joinPoint.proceed();
    }
}
