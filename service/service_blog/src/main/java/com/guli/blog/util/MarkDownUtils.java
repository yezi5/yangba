package com.guli.blog.util;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.net.URLEncoder;

/**
 * @author 叶子
 * @Description 请设置
 * @PackageName com.guli.blog.util
 * @DevelopmentTools IntelliJ IDEA
 * @Data 2021/3/25 星期四 17:03
 */
public class MarkDownUtils {
    private static String filePath;

    private static void init(){
        URL url = MarkDownUtils.class.getClassLoader().getResource("data/flag.md");
        String path = url.getPath();
        filePath = path.substring(0,path.lastIndexOf("/")+1);
    }

    public static HttpServletResponse download(HttpServletResponse response, File file) throws IOException {

        // 清空response
        response.reset();
        // 设置response的Header
        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(file.getName(),"utf-8"));
        response.addHeader("Content-Length", "" + file.length());
        response.setContentType("application/octet-stream");

        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
        BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
        byte[] buff = new byte[1024 * 10];
        int len = 0;
        while ((len = bis.read(buff)) > 0){
            bos.write(buff,0 ,buff.length);
        }
        bos.flush();
        bos.close();

        return response;
    }

    /**
     * 根据文件名创建文件，并将指定内容写入文件
     * @param contextMK
     * @param fileName
     * @return
     * @throws IOException
     */
    public static File createNewFile(String contextMK,String fileName) throws IOException {
        init();
        String fileRealPath = filePath+fileName+".md";
        File file = new File(fileRealPath);
        if (!file.exists()){
            file.createNewFile();
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(contextMK);
            fileWriter.flush();
            fileWriter.close();
        }

        return file;
    }
}
