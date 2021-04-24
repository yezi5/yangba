package com.guli.eduservice.pojo.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Serializable;

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
