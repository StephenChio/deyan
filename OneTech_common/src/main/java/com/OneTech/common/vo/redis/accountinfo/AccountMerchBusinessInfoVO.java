package com.OneTech.common.vo.redis.accountinfo;

import java.util.List;

/**
 * @description 账号下业务配置信息
 * @date 2018年10月26日
 * @author 吴森荣
 * @email wsr@basehe.com
 * @version v1.0
 */
public class AccountMerchBusinessInfoVO {
	private String merchNo;
	private String businessNo;
	private List<AccountInfoVO> accountInfoList;
	
	public String getMerchNo() {
		return merchNo;
	}
	public void setMerchNo(String merchNo) {
		this.merchNo = merchNo;
	}
	public String getBusinessNo() {
		return businessNo;
	}
	public void setBusinessNo(String businessNo) {
		this.businessNo = businessNo;
	}
	public List<AccountInfoVO> getAccountInfoList() {
		return accountInfoList;
	}
	public void setAccountInfoList(List<AccountInfoVO> accountInfoList) {
		this.accountInfoList = accountInfoList;
	}


	
}