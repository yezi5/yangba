package com.guli.vod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import com.guli.vod.client.VodClient;
import com.guli.vod.service.VodService;
import com.guli.vod.utils.ConstantVodUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class VodServiceImpl implements VodService {

    @Autowired
    private VodClient clientUtils;

    @Override
    public Map<String,Object> uploadAlyVideo(MultipartFile file) {
        String filename = file.getOriginalFilename();
        String title = filename.substring(0,filename.lastIndexOf('.'));
        Map<String,Object> map = new HashMap<>();

        try(InputStream stream = file.getInputStream()) {
            UploadStreamRequest request = new UploadStreamRequest(ConstantVodUtils.ACCESS_KEY_ID,
                    ConstantVodUtils.ACCESS_KEY_SECRET,title,filename,stream);

            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);

            if (response.isSuccess()){
                String videoId = response.getVideoId();
                map.put("videoId",videoId);
            }
            map.put("message",response.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public boolean removeAlyVideo(String id) {
        DefaultAcsClient client = clientUtils.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);

        DeleteVideoRequest request = new DeleteVideoRequest();
        request.setVideoIds(id);
        try {
            DeleteVideoResponse response = client.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

}
