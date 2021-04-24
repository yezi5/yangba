package com.guli.eduorder.pojo.vo;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 叶子
 * @Description 请设置
 * @PackageName com.guli.eduorder.pojo.vo
 * @DevelopmentTools IntelliJ IDEA
 * @Data 2021/3/29 星期一 18:52
 */
@Data
public class NativeVo implements Serializable {

    @ApiModelProperty(value = "订单号")
    private String orderNo;

    @ApiModelProperty(value = "课程id")
    private String courseId;

    @ApiModelProperty(value = "课程名称")
    private String courseTitle;

    @ApiModelProperty(value = "讲师名称")
    private String teacherName;

    @ApiModelProperty(value = "订单金额（元）")
    private BigDecimal totalFee;

    @ApiModelProperty(value = "创建时间")
    private String gmtCreate;

    @ApiModelProperty("用户付款无需输入金额(二维码地址)")
    private String zfbuseridcode;

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = DateUtil.format(gmtCreate,"yyyy-MM-dd ss:hh:mm");
    }
}
