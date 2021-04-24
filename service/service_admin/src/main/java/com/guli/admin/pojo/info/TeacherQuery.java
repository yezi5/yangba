package com.guli.admin.pojo.info;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @author 叶子
 * @Description 请设置
 * @PackageName com.guli.admin.pojo.info
 * @DevelopmentTools IntelliJ IDEA
 * @Data 2021/3/31 星期三 18:52
 */
@ApiModel(value = "Teacher查询对象", description = "讲师查询对象封装")
@Data
@Component
public class TeacherQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "教师名称,模糊查询")
    private String name;

    @ApiModelProperty(value = "头衔 1高级讲师 2首席讲师")
    private Integer level;

    @ApiModelProperty(value = "查询开始时间", example = "2019-01-01 10:10:10")
    private String begin;//注意，这里使用的是String类型，前端传过来的数据无需进行类型转换

    @ApiModelProperty(value = "查询结束时间", example = "2019-12-01 10:10:10")
    private String end;

}
