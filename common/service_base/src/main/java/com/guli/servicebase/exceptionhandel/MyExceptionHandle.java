package com.guli.servicebase.exceptionhandel;

import com.guli.commonutils.R;
import com.guli.servicebase.handle.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class MyExceptionHandle {

    /*
     * 全局异常处理
     * */
    @ExceptionHandler(Exception.class)
    @ResponseBody //为了返回数据
    public R error(Exception e) {
        e.printStackTrace();
        return R.error().message("全局异常处理");
    }

    /*
     * 特定异常处理
     * */
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public R error(ArithmeticException e){
        e.printStackTrace();
        return R.error().message("执行了自定义异常");
    }

    /*
     * 自定义异常
     * */
    @ExceptionHandler(BadRequestException.class)
    @ResponseBody
    public R error(BadRequestException e){
        e.printStackTrace();
        return R.error().code(e.getCode()).message(e.getMsg());
    }
}
