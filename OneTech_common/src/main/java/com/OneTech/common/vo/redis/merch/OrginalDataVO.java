package com.OneTech.common.vo.redis.merch;

/**
 * @description redis微信原始id数据
 * @date 2018年9月28日
 * @author 吴森荣
 * @email wsr@basehe.com
 * @version v1.0
 */
public class OrginalDataVO {

	private String merchNo;
	private String businessNo;
	private String appId;
	private String orginalId;
	
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
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getOrginalId() {
		return orginalId;
	}
	public void setOrginalId(String orginalId) {
		this.orginalId = orginalId;
	}
	
	
}
