package com.guli.admin.pojo.vo;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @author 叶子
 * @Description 请设置
 * @PackageName com.guli.admin.pojo.vo
 * @DevelopmentTools IntelliJ IDEA
 * @Data 2021/3/31 星期三 17:43
 */
@Data
@Component
public class VideoVo {

    private String id;

    private String title;

    private Boolean isFree;

    private String videoSourceId;//视频id
}
