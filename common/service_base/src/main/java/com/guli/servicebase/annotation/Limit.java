package com.guli.servicebase.annotation;

import com.guli.servicebase.util.LimitType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 叶子
 * @Description 请设置
 * @DevelopmentTools IntelliJ IDEA
 * @Data 2021/4/4 星期日 11:07
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Limit {
    // 资源名称，用于描述接口功能
    String name() default "";

    // 资源 key
    String key() default "";

    // key prefix
    String prefix() default "";

    // 时间的，单位秒
    int period();

    // 限制访问次数
    int count();

    // 超过访问限制次数后，加入黑名单时间 单位秒
    long banTime() default 60*5;

    // 限制类型
    LimitType limitType() default LimitType.CUSTOMER;
}
