package com.OneTech.common.vo;

public class NewFriendVO {
    private String wechatId;
    private String userName;
    private String imgPath;
    private String verificationMsg;

    public String getWechatId() {
        return wechatId;
    }

    public void setWechatId(String wechatId) {
        this.wechatId = wechatId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getVerificationMsg() {
        return verificationMsg;
    }

    public void setVerificationMsg(String verificationMsg) {
        this.verificationMsg = verificationMsg;
    }
}
