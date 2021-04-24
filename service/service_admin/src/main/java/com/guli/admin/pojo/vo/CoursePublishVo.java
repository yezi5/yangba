package com.guli.admin.pojo.vo;

import lombok.Data;

/**
 * @author 叶子
 * @Description 请设置
 * @PackageName com.guli.admin.pojo.vo
 * @DevelopmentTools IntelliJ IDEA
 * @Data 2021/3/31 星期三 17:24
 */
@Data
public class CoursePublishVo {

    private String id;

    private String title;

    private String cover;

    private Integer lessonNum;

    private String subjectLevelOne;

    private String subjectLevelTwo;

    private String teacherName;

    private String price;
}
