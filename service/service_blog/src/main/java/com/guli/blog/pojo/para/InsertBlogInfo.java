package com.guli.blog.pojo.para;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author 叶子
 * @Description 请设置
 * @PackageName com.guli.blog.pojo.para
 * @DevelopmentTools IntelliJ IDEA
 * @Data 2021/3/24 星期三 17:53
 */
@Data
public class InsertBlogInfo {

    @ApiModelProperty(value = "文章标题")
    private String title;

    @ApiModelProperty(value = "文章内容(markdown语法)")
    private String contextMk;

    @ApiModelProperty(value = "文章内容（html格式）")
    private String contextHtml;
}
