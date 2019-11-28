package com.OneTech.model.model;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
@Table(name = "address_list")
public class AddressListBean {
    @Id
    private String id;
    private String wechatId;
    private String FWechatId;
    private String accpetStatus;
    private String verificationMsg;
    private Date createTime;
    private Date updateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWechatId() {
        return wechatId;
    }

    public void setWechatId(String wechatId) {
        this.wechatId = wechatId;
    }

    public String getFWechatId() {
        return FWechatId;
    }

    public void setFWechatId(String FWechatId) {
        this.FWechatId = FWechatId;
    }

    public String getAccpetStatus() {
        return accpetStatus;
    }

    public void setAccpetStatus(String accpetStatus) {
        this.accpetStatus = accpetStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getVerificationMsg() {
        return verificationMsg;
    }

    public void setVerificationMsg(String verificationMsg) {
        this.verificationMsg = verificationMsg;
    }
}
