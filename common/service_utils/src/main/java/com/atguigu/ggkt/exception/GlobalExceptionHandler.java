package com.atguigu.ggkt.exception;

import com.atguigu.ggkt.result.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

public class GlobalExceptionHandler {
    @ExceptionHandler(GgktException.class)
    @ResponseBody
    public Result error(GgktException e) {
        e.printStackTrace();
        return Result.fail().message(e.getMsg()).code(e.getCode());
    }
}
