package com.guli.admin.pojo.vo;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 叶子
 * @Description 请设置
 * @PackageName com.guli.admin.pojo.vo
 * @DevelopmentTools IntelliJ IDEA
 * @Data 2021/3/31 星期三 17:42
 */
@Data
@Component
public class ChapterVo {

    private String id;

    private String title;

    private List<VideoVo> children = new ArrayList<>();

    private Integer childrenSize;
}
