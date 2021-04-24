package com.guli.eduorder.pojo.pay;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author 叶子
 * @Description 请设置
 * @PackageName com.guli.eduorder.pojo
 * @DevelopmentTools IntelliJ IDEA
 * @Data 2021/3/28 星期日 9:25
 */
@Data
public class OrderMsg {

    @ApiModelProperty("message")
    private String msg;

    @ApiModelProperty("订单其他信息")
    private String other;

    @ApiModelProperty("状态码")
    private String code;

    @ApiModelProperty("订单ID")
    private String orderId;

    @ApiModelProperty("价格")
    private String price;

    @ApiModelProperty("商品名称")
    private String name;

    @ApiModelProperty("跳转url")
    private String reurl;

    @ApiModelProperty("第三方用户ID")
    private String thirduid;

    @ApiModelProperty("实付金额")
    private String originalprice;

    @ApiModelProperty("订单备注")
    private String remarks;

    @ApiModelProperty("用户付款无需输入金额(二维码地址)")
    private String zfbuseridcode;

    @ApiModelProperty("微信收款码地址")
    private String wxcode;

    @ApiModelProperty("支付宝收款码地址")
    private String zfbcode;

    @ApiModelProperty("拉起支付宝使用")
    private String qrcode;
}
