package org.clm.demo.entity;

import lombok.Data;

import java.util.List;

/** 
* @ClassName: BaseRequest 
* @Description: REST接口通用响应类  
* @author 饶翔
* @date 2015年7月17日 上午10:39:29
*/
@Data
public class BaseResponse<T> {
	
	private ResultCode resultCode;	//结果状态码
	
	//private List<T> data;			//数据集

	private T data;
	
	private Object otherData;	//其它数据
	
	private String message;			//提示消息

}
