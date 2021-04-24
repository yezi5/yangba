package com.guli.vod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface VodService {
    Map<String,Object> uploadAlyVideo(MultipartFile file);

    boolean removeAlyVideo(String id);
}
