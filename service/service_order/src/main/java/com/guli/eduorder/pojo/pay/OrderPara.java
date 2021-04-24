package com.guli.eduorder.pojo.pay;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 叶子
 * @Description 请设置
 * @PackageName com.guli.eduorder.pojo.pay
 * @DevelopmentTools IntelliJ IDEA
 * @Data 2021/3/28 星期日 9:31
 */
@Data
public class OrderPara {

    @ApiModelProperty("价格")
    private Float price;

    @ApiModelProperty("商品名称")
    private String name;

    @ApiModelProperty("跳转url")
    private String reurl;

    @ApiModelProperty("第三方用户ID")
    private String thirduid;

    @ApiModelProperty("订单备注")
    private String remarks;

    @ApiModelProperty("订单其他信息")
    private String other;

    @ApiModelProperty("回调地址")
    private String callbackurl;
}
