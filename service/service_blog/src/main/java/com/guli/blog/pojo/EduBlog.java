package com.guli.blog.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 博客
 * </p>
 *
 * @author 叶子
 * @since 2021-03-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="EduBlog对象", description="博客")
public class EduBlog implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "博客主键")
    @TableId(value = "blog_id", type = IdType.ID_WORKER_STR)
    private String blogId;

    @ApiModelProperty(value = "用户主键")
    private String blogMemberId;

    @ApiModelProperty(value = "文章标题")
    private String title;

    @ApiModelProperty(value = "文章内容(markdown语法)")
    private String contextMk;

    @ApiModelProperty(value = "文章内容（html格式）")
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

    public void setTitle(String title) {
        this.title = title.trim();
    }
}
