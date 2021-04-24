package com.guli.commonutils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MultiReadHttpServletRequest extends HttpServletRequestWrapper {
    public String _body; //
 
    public MultiReadHttpServletRequest(HttpServletRequest request) throws IOException {
        super(request);
        /*ByteBuffer byteBuffer = ByteBuffer.allocate(1024 * 200);*/
        StringBuffer sBuffer = new StringBuffer();

        BufferedReader bufferedReader = request.getReader();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            sBuffer.append(line);
        }

        /*BufferedInputStream bis = new BufferedInputStream(request.getInputStream());
        byte[] buff = new byte[1024 * 100];
        int len = 0;
        while ((len = bis.read(buff)) > 0){
            byteBuffer.put(buff,0,len);
        }
*/
        _body = sBuffer.toString();
        /*_body = byteBuffer;*/
    }
 
    @Override
    public ServletInputStream getInputStream() {
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(_body.getBytes());
        return new ServletInputStream() {
            @Override
            public int read() {
                return byteArrayInputStream.read();
            }
 
            @Override
            public boolean isFinished() {
                return false;
            }
 
            @Override
            public boolean isReady() {
                return false;
            }
 
            @Override
            public void setReadListener(ReadListener listener) {
 
            }
        };
    }
 
    @Override
    public BufferedReader getReader() {
        return new BufferedReader(new InputStreamReader(this.getInputStream()));
    }
}