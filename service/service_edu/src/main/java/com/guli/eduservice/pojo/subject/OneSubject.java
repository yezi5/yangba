package com.guli.eduservice.pojo.subject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OneSubject {
    private String id;
    private String parent_id;
    private String title;
    private List<TwoSubject> children;
}
