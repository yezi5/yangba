package com.guli.eduorder.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 叶子
 * @Description 请设置
 * @PackageName com.guli.eduorder.pojo
 * @DevelopmentTools IntelliJ IDEA
 * @Data 2021/3/20 星期六 17:43
 */
@Data
public class Parameter implements Serializable {
    private String courseId;
    private String token;
}
