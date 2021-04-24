package com.guli.eduservice.pojo.vo;

import cn.hutool.core.date.DateUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author 叶子
 * @Description 请设置
 * @DevelopmentTools IntelliJ IDEA
 * @Data 2021/4/8 星期四 21:17
 */
@Data
public class HistoryVo {
    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "课程封面")
    private String cover;

    @ApiModelProperty(value = "课程标题")
    private String title;

    @ApiModelProperty(value = "浏览时间")
    private String gmtCreate;

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = DateUtil.format(gmtCreate,"yyyy-MM-dd hh:mm:ss");
    }
}
