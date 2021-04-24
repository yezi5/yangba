package com.guli.eduservice.pojo.para;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 叶子
 * @Description 请设置
 * @DevelopmentTools IntelliJ IDEA
 * @Data 2021/4/22 星期四 19:50
 */
@Data
public class PublishCommentPara {

    @ApiModelProperty(value = "（视频资源Id）")
    private String blogId;

    @ApiModelProperty(value = "被回复评论id")
    private String talked;

    @ApiModelProperty(value = "评论内容")
    private String textarea;
}
