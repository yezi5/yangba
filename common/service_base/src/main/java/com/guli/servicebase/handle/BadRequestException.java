package com.guli.servicebase.handle;

import com.guli.commonutils.ResultCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor //有参构造
public class BadRequestException extends RuntimeException {

    private Integer code; //状态码

    private String msg; //异常信息

    public BadRequestException(){
        this.code = ResultCode.BAN;
        this.msg = "您的访问过于频繁，请稍后再试！";
    }
}