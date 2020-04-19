package org.clm.demo.exception;


import lombok.extern.slf4j.Slf4j;
import org.clm.demo.entity.BaseResponse;
import org.clm.demo.entity.ResultCode;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * springboot 的全局异常处理
 *
 * 比如抛出CommonException,就是commonExceptionHandler处理异常
 * 抛出Exception 就是 exceptionHandler 处理异常
 *
 * 可以根据配置不同的异常处理handler，处理对应的异常
 *
 */
@ControllerAdvice
@Slf4j
public class SpringbootCommonException {
    /**
     * 捕获自定义异常
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(CommonException.class)
    public BaseResponse commonExceptionHandler(CommonException e) {
        log.error("进入全局异常处理器", e.getMessage(), e);

        BaseResponse response = new BaseResponse();
        response.setMessage(e.getMessage());
        response.setResultCode(ResultCode.RESULT_ERROR);

        return response;
    }

    /**
     * 处理exception异常
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value =Exception.class)
    @ResponseBody
    public BaseResponse exceptionHandler(HttpServletRequest req, Exception e){
        log.error("未知异常！原因是:",e.getMessage(),e);

        BaseResponse response = new BaseResponse();
        response.setMessage(e.getMessage());
        response.setResultCode(ResultCode.RESULT_ERROR);

        return response;
    }

}
