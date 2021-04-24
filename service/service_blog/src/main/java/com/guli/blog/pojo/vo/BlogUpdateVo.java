package com.guli.blog.pojo.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.guli.blog.util.StringUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author 叶子
 * @Description 请设置
 * @PackageName com.guli.blog.pojo.vo
 * @DevelopmentTools IntelliJ IDEA
 * @Data 2021/3/27 星期六 12:24
 */
@Data
public class BlogUpdateVo {
    @ApiModelProperty(value = "博客主键")
    private String blogId;

    @ApiModelProperty(value = "文章标题")
    private String title;

    @ApiModelProperty(value = "文章内容(markdown语法)")
    private String contextMk;
}
