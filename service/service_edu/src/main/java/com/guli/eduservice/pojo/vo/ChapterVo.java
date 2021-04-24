package com.guli.eduservice.pojo.vo;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Data
@Component
public class ChapterVo {

    private String id;

    private String title;

    private List<VideoVo> children = new ArrayList<>();

    private Integer childrenSize;
}