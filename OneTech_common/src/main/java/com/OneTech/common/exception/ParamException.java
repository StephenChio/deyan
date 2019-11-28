package com.OneTech.common.exception;

import com.OneTech.common.constants.SystemConstants;
import com.OneTech.common.constants.TipsConstants;

/**
 * @description 系统自定义异常
 * @date 2018年8月24日
 * @author 吴森荣
 * @email wsr@basehe.com
 * @version v1.0
 */
public class ParamException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String respCode;
	private String msgKey;
	
	public String getRespCode() {
		return respCode;
	}
	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}
	public String getMsgKey() {
		return msgKey;
	}
	public void setMsgKey(String msgKey) {
		this.msgKey = msgKey;
	}
	
	public ParamException(String respCode, String msgKey) {
		super();
		this.respCode = respCode;
		this.msgKey = msgKey;
	}
	public static ParamException error() {
		return new ParamException(SystemConstants.RESPONSE_FAIL, TipsConstants.OPERATE_FAIL);
	}
	
	
}
