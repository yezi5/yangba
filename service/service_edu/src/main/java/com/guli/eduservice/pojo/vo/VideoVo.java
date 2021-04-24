package com.guli.eduservice.pojo.vo;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class VideoVo {

    private String id;

    private String title;

    private Boolean isFree;

    private String videoSourceId;//视频id
}