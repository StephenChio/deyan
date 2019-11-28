package com.OneTech.common.constants;

/**
 * @description 通过接口获取redis数据常量
 * @date 2018年10月24日
 * @author 吴森荣
 * @email wsr@basehe.com
 * @version v1.0
 */
public class ApiRedisConstants {

	/**
	 * 1、根据微信原始id获取数据
	 */
	public static final String REDIS_DATA_TYPE_ORGINAL = "getOrginalData";
	
	/**
	 * 2、根据商户号获取数据
	 */
	public static final String REDIS_DATA_TYPE_MERCHINFO = "getMerchInfoData";
	
	/**
	 * 3、根据accountId获取账号权限
	 */
	public static final String REDIS_DATA_TYPE_PERMISSIONS = "getAccountPermission";
	
	/**
	 * 4、获取系统内商户业务号下的账号列表
	 */
	public static final String REDIS_DATA_TYPE_ACCOUNT_INFO = "getAccountInfoList";
	
	/**
	 * 5、获取系统内所有商户号-业务号
	 */
	public static final String REDIS_DATA_TYPE_ALL_MERCH_BUSINESS = "getAllMerchBusiness";
	
	/**
	 * 6、根据账号id获取业务号配置信息
	 */
	public static final String REDIS_DATA_TYPE_ACCOUNT_SETTING_INFO = "getAccountSettingInfo";
	
	
	
	
	
	
	
}
