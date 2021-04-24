package com.guli.oss.controller;

import com.guli.commonutils.R;
import com.guli.oss.service.OssService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/eduoss/fileoss")
@CrossOrigin(allowCredentials = "true")
@Api(tags = "OSS业务接口")
public class OssController {

    @Autowired
    private OssService service;

    @ApiOperation(value = "上传文件")
    @PostMapping
    public R uploadOssFile(@ApiParam(name = "文件对象",value = "要上传的文件",required = true) MultipartFile file){

        String url = service.uploadFileAvatar(file);

        return R.ok().data("url",url);
    }

    @DeleteMapping("delete/{fileName}")
    public R deleteFile(@PathVariable String fileName){
        boolean rs = service.deleteList(fileName);

        return rs?R.ok():R.error();
    }
}
