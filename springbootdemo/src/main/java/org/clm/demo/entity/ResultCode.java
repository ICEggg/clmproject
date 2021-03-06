package org.clm.demo.entity;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public enum ResultCode {

	RESULT_SUCCESS(1000),		//操作成功
	
	RESULT_BAD_REQUEST(1001),	//错误的请求
	
	RESULT_EXIST(1002),			//已存在
	
	RESULT_ERROR(1003),			//操作失败
	
	RESULT_OUTOFDATE(1004),		//过期
	
	RESULT_NOT_EXIST(1005),		//不存在

    RESULT_NOT_AUTHORIZED(401); //未授权
	
	private final int code;
	
	public int getCode() {
        return code;
    }
	
	ResultCode(int code) {
        this.code = code;
    }
	
}
