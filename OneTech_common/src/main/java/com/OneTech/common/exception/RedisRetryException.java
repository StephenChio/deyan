package com.OneTech.common.exception;

/**
 * @description redis自定义异常
 * @date 2018年8月7日
 * @author 吴森荣
 * @email wsr@basehe.com
 * @version v1.0
 */
public class RedisRetryException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String key;
	private String value;
	private String fileds;
	private int seconds;
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getFileds() {
		return fileds;
	}
	public void setFileds(String fileds) {
		this.fileds = fileds;
	}
	public int getSeconds() {
		return seconds;
	}
	public void setSeconds(int seconds) {
		this.seconds = seconds;
	}
	public RedisRetryException(String key, String value, String fileds, int seconds) {
		super();
		this.key = key;
		this.value = value;
		this.fileds = fileds;
		this.seconds = seconds;
	}
	public RedisRetryException() {
		super();
	}
	
	
}
