package com.OneTech.common.constants;

/**
 * @description 系统常量
 * @date 2018年7月17日
 * @author 吴森荣
 * @email wsr@basehe.com
 * @version v1.0
 */
/**
 * @description 
 * @date 2018年11月21日
 * @author 吴森荣
 * @email wsr@basehe.com
 * @version v1.0
 */
public class SystemConstants {

	/**
	 * 响应成功，200
	 */
	public final static String RESPONSE_SUCCESS = "200";
	
	/**
	 * 处理中，201
	 * */
	public final static String RESPONSE_PROCESS = "201";
	
	
	/**
	 * 响应失败，301
	 * */
	public final static String RESPONSE_FAIL = "301";
	
	
	/**
	 * 不允许登录的账号，可能是商户，业务被禁用
	 */
	public final static String NOT_ALLOW_LOGIN = "1001";
	
	/**
	 * http响应码,401,需重新登录
	 * */
	public final static Integer NEED_RELOGIN = 401;
	
	
	/**
	 * 拒绝访问，可能为无权限或链接不存在
	 */
	public final static Integer ACCESS_DENIED = 403;
	
	/**
	 * 业务数据分隔符
	 */
	public static final String BUSINESS_SEPARATED = "_";
	
	/**
	 * 客服接口响应成功码：success
	 */
	public static final String INTERFACE_RESPONSE_SUCCESS = "success";
	
}
