package javaproject.java.udfexception;



/**
 * 自定义异常
 * (还没用过)
 */

public class CommonException extends Exception  {

    private ResultCode resultCode;

    public CommonException(ResultCode resultCode) {
        this.resultCode = resultCode;
    }

    public ResultCode getResultCode() {
        return resultCode;
    }
}
