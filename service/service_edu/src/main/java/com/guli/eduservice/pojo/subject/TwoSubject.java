package com.guli.eduservice.pojo.subject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TwoSubject {
    private String id;
    private String parent_id;
    private String title;
}
