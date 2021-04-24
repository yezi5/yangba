package com.guli.admin.pojo.vo;

import cn.hutool.core.date.DateUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 叶子
 * @Description 请设置
 * @DevelopmentTools IntelliJ IDEA
 * @Data 2021/4/9 星期五 11:13
 */
@Data
public class OrderVo {

    @ApiModelProperty(value = "订单号")
    private String orderNo;

    @ApiModelProperty(value = "课程名称")
    private String courseTitle;

    @ApiModelProperty(value = "会员昵称")
    private String nickname;

    @ApiModelProperty(value = "支付完成时间")
    private String payTime;

    @ApiModelProperty(value = "订单金额（元）")
    private BigDecimal totalFee;

    @ApiModelProperty(value = "支付类型（1：微信 2：支付宝 3：人工补单）")
    private String payType;

    @ApiModelProperty(value = "订单状态（0：未支付 1：已支付）")
    private String status;

    @ApiModelProperty(value = "创建时间")
    private String gmtCreate;

    public void setPayTime(Date payTime) {
        this.payTime = DateUtil.format(payTime,"yyyy-MM-dd hh:mm:ss");
    }

    public void setPayType(Integer payType) {
        switch (payType){
            case 1:
                this.payType="微信";
                break;
            case 2:
                this.payType="支付宝";
                break;
            case 3:
                this.payType="人工补单";
                break;
            default:
                this.payType="暂未支付";
                break;
        }
    }

    public void setStatus(Integer status) {
        if (status == 1){
            this.status = "已支付";
        }else {
            this.status = "未支付";
        }
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = DateUtil.format(gmtCreate,"yyyy-MM-dd hh:mm:ss");
    }
}
