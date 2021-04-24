package com.guli.eduorder.util;

import com.alibaba.fastjson.JSONObject;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * @author 叶子
 * @Description 请设置
 * @PackageName com.guli.eduorder.util
 * @DevelopmentTools IntelliJ IDEA
 * @Data 2021/3/28 星期日 10:01
 */
@Component
public class OkhttpClient {

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    OkHttpClient client = new OkHttpClient();
    public String post(String url, Map<String,Object> para, Map<String,String> headerMap) throws IOException {
        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        for (String key : para.keySet()) {
            formBodyBuilder.addEncoded(key, String.valueOf(para.get(key)));
        }
        FormBody formBody = formBodyBuilder.build();

        Request.Builder builder = new Request.Builder();
        if (headerMap!=null && !headerMap.isEmpty()){
            for (String key : headerMap.keySet()) {
                builder.addHeader(key,headerMap.get(key));
            }
        }
        Request request = builder.url(url).post(formBody).build();

        Response response = client.newCall(request).execute();

        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }

}
