package com.guli.eduservice.pojo.vo;

import cn.hutool.core.date.DateUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author 叶子
 * @Description 请设置
 * @DevelopmentTools IntelliJ IDEA
 * @Data 2021/4/7 星期三 19:04
 */
@Data
public class CommentVo {

    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "课程主键")
    private String courseId;

    @ApiModelProperty(value = "被回复评论id")
    private String commonParentId;

    @ApiModelProperty(value = "被回复评论id")
    private String commonParentUserName;

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
    private String gmtCreate;

    public List<CommentChildVo> child;

    public int childSize;

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = DateUtil.format(gmtCreate,"yyyy-MM-dd hh:mm:ss");
    }
}
