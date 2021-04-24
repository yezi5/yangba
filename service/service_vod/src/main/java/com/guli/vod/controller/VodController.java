package com.guli.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.guli.commonutils.R;
import com.guli.servicebase.handle.BadRequestException;
import com.guli.vod.client.VodClient;
import com.guli.vod.service.VodService;
import com.guli.vod.utils.ConstantVodUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * <p>
 *     视频服务控制器
 * </p>
 *
 * @author 叶子
 * @since 2020-10-12
 */
@RestController
@RequestMapping("/eduvod/video")
@CrossOrigin(allowCredentials = "true")
@Api(tags = "阿里云视频业务接口")
public class VodController {

    @Autowired
    private VodService service;

    /**
     * 成功
     * @param file
     * @return
     */
    @ApiOperation(value = "上传视频")
    @PostMapping("uploadAlyiVideo")
    public R uploadAlyVideo(@ApiParam(name = "视频文件对象",value = "要上传的视频",required = true) MultipartFile file){
        System.out.println(1111111111);
        Map<String, Object> map = service.uploadAlyVideo(file);

        if (map.containsKey("videoId")){
            return R.ok().data(map);
        }

        return R.error().data(map);
    }

    /**
     * 成功
     * @param videoId
     * @return
     */
    @ApiOperation(value = "删除视频")
    @DeleteMapping("removeAlyVideo/{videoId}")
    public R removeAlyVideo(@ApiParam(name = "视频id",value = "视频id",required = true) @PathVariable String videoId){
        boolean delete = service.removeAlyVideo(videoId);
        System.out.println(delete);
        return R.ok();
    }

    @GetMapping("getPlayAuth/{id}")
    public R getPlayAuth(@PathVariable String id) {
        try {
            //创建初始化对象
            DefaultAcsClient client = VodClient.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
            //创建获取凭证request和response对象
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            //向request设置视频id
            request.setVideoId(id);
            //调用方法得到凭证
            GetVideoPlayAuthResponse response = client.getAcsResponse(request);
            String playAuth = response.getPlayAuth();
            return R.ok().data("playAuth",playAuth);
        }catch(Exception e) {
            throw new BadRequestException(20001,"获取凭证失败");
        }
    }
}
