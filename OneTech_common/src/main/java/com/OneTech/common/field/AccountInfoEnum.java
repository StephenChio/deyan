package com.OneTech.common.field;

/**
 * @description 账号信息枚举类
 * @date 2018年8月10日
 * @author 吴森荣
 * @email wsr@basehe.com
 * @version v1.0
 */
public enum AccountInfoEnum {
	
	ACCOUNT_ID,
	ACCOUNT_NAME,
	//session 中存放账户的key,对应value为：{"ACCOUNT_ID":"","ACCOUNT_NAME":""}
	SESSION_ACCOUNT_NO,
	MERCH_ADMIN_SYS,
	MERCH_FRONT_SYS,
	BUSINESS_DEFINE_SYS,
	TOPIC_DESIGN_SYS,
	CUSTOMER_ADMIN_SYS,
	CUSTOMER_FRONT_SYS,
	UNION_LIFE_WORK_UNIT//合众保险项目--任务单元控制后台
	
	
}
