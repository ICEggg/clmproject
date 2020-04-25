package org.clm.demo.exception;


import lombok.extern.slf4j.Slf4j;
import org.clm.demo.entity.ResultCode;

/**
 * 自定义异常
 */

public class CommonException extends Exception  {
    private ResultCode resultCode;

    public CommonException(String message) {
        super(message);
    }

    public CommonException(ResultCode resultCode) {
        this.resultCode = resultCode;
    }

    public CommonException(String message,ResultCode resultCode) {
        super(message);
        this.resultCode = resultCode;
    }

    public ResultCode getResultCode() {
        return resultCode;
    }


}
