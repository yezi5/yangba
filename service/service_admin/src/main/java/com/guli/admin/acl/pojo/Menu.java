package com.guli.admin.acl.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 菜单
 * </p>
 *
 * @author 叶子
 * @since 2021-03-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Menu对象", description="菜单")
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "menu_id", type = IdType.ID_WORKER_STR)
    private String menuId;

    @ApiModelProperty(value = "父级菜单id")
    private String menuParentId;

    @ApiModelProperty(value = "图标class属性")
    private String icon;

    @ApiModelProperty(value = "路由")
    private String menuIndex;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "菜单状态（1可用，0禁用）")
    private Integer menuStatus;


}
