package com.guli.admin.pojo.excel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author 叶子
 * @Description 请设置
 * @PackageName com.guli.admin.pojo.excel
 * @DevelopmentTools IntelliJ IDEA
 * @Data 2021/3/31 星期三 18:41
 */
@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TwoSubject {
    private String id;
    private String parent_id;
    private String title;
}