package com.guli.admin.pojo.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author 叶子
 * @Description 请设置
 * @PackageName com.guli.admin.pojo.excel
 * @DevelopmentTools IntelliJ IDEA
 * @Data 2021/3/31 星期三 18:37
 */
@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubjectData {
    @ExcelProperty(index = 0) //表示这是第一列
    private String oneSubjectName;

    @ExcelProperty(index = 1) //表示这是第二列
    private String twoSubjectName;
}

