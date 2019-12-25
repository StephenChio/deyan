package com.OneTech.common.vo;

import com.OneTech.common.constants.SystemConstants;
import com.OneTech.common.constants.TipsConstants;
import com.OneTech.common.field.CommonMessageEnum;
import lombok.Data;
/**
 * 前端交互实体
 * @description 
 * @date 2018年7月13日
 * @author 吴森荣
 * @email wsr@basehe.com
 * @version v1.0
 */
@Data
public class StatusBean<T> {

	private String respCode;
	private String respMsg;
	private String token;
	private T data;
	
	public String getRespCode() {
		return respCode;
	}
	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}
	public String getRespMsg() {
		return respMsg;
	}
	public void setRespMsg(String respMsg) {
		this.respMsg = respMsg;
	}
	
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	public StatusBean(String respCode) {
		super();
		this.respCode = respCode;
	}
	
	public StatusBean(String respCode, String respMsg) {
		super();
		this.respCode = respCode;
		this.respMsg = respMsg;
	}
	
	public StatusBean(String respCode, String msgKey, String respMsg, T data) {
		super();
		this.respCode = respCode;
		this.respMsg = respMsg;
		this.data = data;
	}
	
	public StatusBean(String respCode, String respMsg, T data) {
		super();
		this.respCode = respCode;
		this.respMsg = respMsg;
		this.data = data;
	}
	
	@SuppressWarnings({ "rawtypes" })
	public static StatusBean success() {
		return new StatusBean(SystemConstants.RESPONSE_SUCCESS, TipsConstants.OPERATE_SUCCESS);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static StatusBean success(Object obj) {
		return new StatusBean(SystemConstants.RESPONSE_SUCCESS,TipsConstants.OPERATE_SUCCESS,obj);
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static StatusBean success(String message) {
		return new StatusBean(SystemConstants.RESPONSE_SUCCESS,message,null);
	}

	@SuppressWarnings({ "rawtypes" })
	public static StatusBean successMsg() {
		return new StatusBean(SystemConstants.RESPONSE_SUCCESS, CommonMessageEnum.MESSAGE_OPERATION_SUCCESS.toString(),TipsConstants.OPERATE_SUCCESS);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static StatusBean successMsg(Object obj) {
		return new StatusBean(SystemConstants.RESPONSE_SUCCESS,CommonMessageEnum.MESSAGE_OPERATION_SUCCESS.toString(),TipsConstants.OPERATE_SUCCESS,obj);
	}

	@SuppressWarnings({ "rawtypes" })
	public static StatusBean fail() {
		return new StatusBean(SystemConstants.RESPONSE_FAIL,TipsConstants.OPERATE_FAIL);
	}
	@SuppressWarnings({ "rawtypes" })
	public static StatusBean fail(String errorMsg) {
		return new StatusBean(SystemConstants.RESPONSE_FAIL,errorMsg);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static StatusBean fail(Object obj) {
		return new StatusBean(SystemConstants.RESPONSE_FAIL,TipsConstants.OPERATE_FAIL,obj);
	}

	@SuppressWarnings({ "rawtypes" })
	public static StatusBean failMsg() {
		return new StatusBean(SystemConstants.RESPONSE_FAIL,CommonMessageEnum.MESSAGE_OPERATION_FAIL.toString());
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static StatusBean failMsg(Object obj) {
		return new StatusBean(SystemConstants.RESPONSE_FAIL,CommonMessageEnum.MESSAGE_OPERATION_FAIL.toString(),obj);
	}
	
	public StatusBean() {
		super();
	}
	
	
	
}
