package com.guli.blog.pojo.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.guli.blog.util.StringUtil;
import com.guli.commonutils.HtmlUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 叶子
 * @Description 请设置
 * @PackageName com.guli.blog.pojo.vo
 * @DevelopmentTools IntelliJ IDEA
 * @Data 2021/3/25 星期四 13:19
 */
@Data
public class BlogListVo implements Serializable {

    @ApiModelProperty(value = "博客主键")
    @TableId(value = "blog_id", type = IdType.ID_WORKER_STR)
    private String blogId;

    @ApiModelProperty(value = "文章标题")
    private String title;

    @ApiModelProperty(value = "文章内容（纯文本格式）")
    private String contextHtml;

    @ApiModelProperty(value = "浏览数")
    private Long blogView;

    @ApiModelProperty(value = "收藏数")
    private Long blogStar;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @ApiModelProperty(value = "修改时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;

    @ApiModelProperty(value = "点赞数")
    private Long blogLike;

    @ApiModelProperty(value = "作者")
    private String author;

    public void setContextHtml(String contextHtml) {
        contextHtml = StringUtil.decode(contextHtml);
        this.contextHtml = HtmlUtils.convert(contextHtml);
    }
}
