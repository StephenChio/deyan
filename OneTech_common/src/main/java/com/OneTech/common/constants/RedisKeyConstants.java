package com.OneTech.common.constants;

/**
 * @description redis--key常量类
 * @date 2018年10月11日
 * @author 吴森荣
 * @email wsr@basehe.com
 * @version v1.0
 */
public class RedisKeyConstants {

	/**
	 * 权限
	 */
	public static final String PERMISSIONS_KEY = "PERMISSIONS_";
	/**
	 * 登陆的账号信息redis缓存key ACCOUNTINFO_(固定前缀) + merchNo(商户号) + businessNo(业务号)
	 */
	public static final String ACCOUNTINFO_KEY = "ACCOUNTINFO_";
	/**
	 * 商户业务号列表，list内数据为字符串 (merchNo+businessNo)
	 */
	public static final String MERCH_BUSINESS_KEY = "MERCH_BUSINESS";
	/**
	 * 微信原始id  数据
	 */
	public static final String ORIGINAL_KEY = "ORIGINAL_";
	/**
	 * 商户号 数据
	 */
	public static final String MERCHINFO_KEY = "MERCHINFO_";
	
	/**
	 * 账号下的业务配置信息
	 */
	public static final String ACCOUNT_BUSINESS_SETTING_INFO = "ACCOUNT_BUSINESS_SETTING_INFO_";
	
	/**
	 * jwt：redis-token
	 */
	public static final String JWT_KEY = "GUQINUO_JWT_TOKEN_";
	
	
	/**
	 * refresh：refresh_token_key
	 */
	public static final String REFRESH_TOKEN_KEY = "GUQINUO_JWT_TOKEN_";
	
	
	/**
	 * Redis分隔符
	 */
	public static final String SEPARATOR = "_";
	
}
