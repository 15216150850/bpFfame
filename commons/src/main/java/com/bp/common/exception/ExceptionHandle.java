package com.bp.common.exception;

import com.bp.common.bean.ReturnBean;
import com.bp.common.config.FileAop;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理器
 * @auther: 钟欣凯
 * @date: 2019/3/21 09:04
 */
@ControllerAdvice
public class ExceptionHandle {

    private static final Logger logger = LoggerFactory.getLogger(FileAop.class);
    @ExceptionHandler(value = RuntimeException.class)
    @ResponseBody
    public ReturnBean bpExceptionHandle(RuntimeException e) {
         logger.error(e.getMessage(),e);
        if (e instanceof BpException){
            if (e.getMessage()!=null){
                return ReturnBean.error(e.getMessage());
        } else {
                return ReturnBean.error();
            }

        }
        e.printStackTrace();
        return ReturnBean.error();
    }
}
