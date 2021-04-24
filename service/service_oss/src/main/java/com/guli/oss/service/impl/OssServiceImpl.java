package com.guli.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.DeleteObjectsRequest;
import com.aliyun.oss.model.DeleteObjectsResult;
import com.guli.oss.service.OssService;
import com.guli.oss.utils.ConstantPropertiesUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

@Service
public class OssServiceImpl implements OssService {

    @Override
    public String uploadFileAvatar(MultipartFile file) {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = ConstantPropertiesUtils.END_POINT;
        // 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建。
        String accessKeyId = ConstantPropertiesUtils.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.ACCESS_KEY_SECRET;
        String bucketName = ConstantPropertiesUtils.BUCKET_NAME;

        String url = null;

        //构建日期路径：avatar/2019/02/26/文件名
        String filePath = new DateTime().toString("yyyy/MM/dd");
        //文件名：uuid.扩展名
        String original = file.getOriginalFilename();
        String fileName = UUID.randomUUID().toString().replaceAll("-","");
        String fileType = original.substring(original.lastIndexOf("."));
        String newName = fileName + fileType;
        String fileUrl = filePath + "/" + newName;
        System.out.println(fileUrl);

        OSS ossClient = null;
        try {
            // 创建OSSClient实例。
            ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            InputStream inputStream = file.getInputStream();
            // 上传文件流。
            ossClient.putObject(bucketName, fileUrl, inputStream);
            url = "https://" + bucketName + "." + endpoint + "/" + fileUrl;
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            // 关闭OSSClient。
            if (ossClient != null){
                ossClient.shutdown();
            }
        }

        return url;
    }

    @Override
    public boolean deleteList(String fileName) {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = ConstantPropertiesUtils.END_POINT;
        // 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建。
        String accessKeyId = ConstantPropertiesUtils.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.ACCESS_KEY_SECRET;
        String bucketName = ConstantPropertiesUtils.BUCKET_NAME;
        OSS ossClient = null;
        try {
            // 创建OSSClient实例。
            ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 删除文件。如需删除文件夹，请将ObjectName设置为对应的文件夹名称。如果文件夹非空，则需要将文件夹下的所有object删除后才能删除该文件夹。
            ossClient.deleteObject(bucketName, fileName);
        }catch (Exception e){
            // 关闭OSSClient。
            if (ossClient!=null){
                ossClient.shutdown();
            }
            return false;
        }
        return true;
    }
}
