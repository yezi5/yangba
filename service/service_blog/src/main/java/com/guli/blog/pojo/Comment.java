package com.guli.blog.pojo;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 博客评论
 * </p>
 *
 * @author 叶子
 * @since 2021-04-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("blog_comment")
@ApiModel(value="Comment对象", description="博客评论")
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    @ApiModelProperty(value = "博客主键")
    private String blogId;

    @ApiModelProperty(value = "被回复评论id")
    private String commonParentId;

    @ApiModelProperty(value = "用户id")
    private String userId;

    @ApiModelProperty(value = "用户头像")
    private String avatar;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "评论内容")
    private String context;

    @ApiModelProperty(value = "点赞人数")
    private Long likeCount;

    @ApiModelProperty(value = "点踩人数")
    private Long averseCount;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    public void setCommonParentId(String commonParentId) {
        if (StringUtils.isEmpty(commonParentId)){
            this.setCommonParentId("0");
        }else {
            this.commonParentId = commonParentId;
        }
    }
}
