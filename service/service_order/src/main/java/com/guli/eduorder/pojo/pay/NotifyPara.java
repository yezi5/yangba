package com.guli.eduorder.pojo.pay;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 叶子
 * @Description 请设置
 * @PackageName com.guli.eduorder.pojo.pay
 * @DevelopmentTools IntelliJ IDEA
 * @Data 2021/3/28 星期日 10:34
 */
@Data
public class NotifyPara {

    @ApiModelProperty("状态码")
    private String code;

    @ApiModelProperty("message")
    private String msg;

    @ApiModelProperty("订单其他信息")
    private String other;

    @ApiModelProperty("订单ID")
    private String orderId;

    @ApiModelProperty("价格")
    private String price;

    @ApiModelProperty("实付金额")
    private String originalprice;

    @ApiModelProperty("商品名称")
    private String name;

    @ApiModelProperty("跳转url")
    private String reurl;

    @ApiModelProperty("第三方用户ID")
    private String thirduid;

    @ApiModelProperty("第三方用户ID")
    private String paytype;

    @ApiModelProperty("订单备注")
    private String remarks;
}
