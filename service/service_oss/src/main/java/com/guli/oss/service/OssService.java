package com.guli.oss.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface OssService {
    String uploadFileAvatar(MultipartFile file);

    boolean deleteList(String fileName);
}
