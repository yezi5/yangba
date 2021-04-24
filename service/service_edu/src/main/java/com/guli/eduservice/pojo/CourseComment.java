package com.guli.eduservice.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 课程评论
 * </p>
 *
 * @author 叶子
 * @since 2021-04-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="CourseComment对象", description="课程评论")
public class CourseComment implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    @ApiModelProperty(value = "课程主键")
    private String courseId;

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
    private Date gmtCreate;


}
