package com.guli.eduservice.pojo;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
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
 * 浏览记录
 * </p>
 *
 * @author 叶子
 * @since 2021-04-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="History对象", description="浏览记录")
public class History implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "课程主键")
    private String courseId;

    @ApiModelProperty(value = "用户主键")
    private String userId;

    @ApiModelProperty(value = "浏览时间")
    private Date gmtCreate;

    public History() {
        this.id = IdUtil.randomUUID();
        this.gmtCreate = new Date();
    }

    public History(String courseId, String userId) {
        this.courseId = courseId;
        this.userId = userId;
        this.id = IdUtil.randomUUID();
        this.gmtCreate = new Date();
    }
}
