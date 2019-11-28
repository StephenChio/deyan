package com.OneTech.common.vo.redis.accountinfo;

/**
 * @description redis账号信息
 * @date 2018年10月11日
 * @author 吴森荣
 * @email wsr@basehe.com
 * @version v1.0
 */
public class AccountInfoVO {

	private String accountNo;
	private String accountName;
	private String accountType;

	
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	
}
