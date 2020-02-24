package com.OneTech.common.vo;

import java.util.Date;

public class AnswerListVO {
    private String id;
    private String wechatId;
    private String userName;
    private String imgPath;
    private String answerText;
    private String likeNum;
    private String agreeNum;
    private String commentsNum;
    private String nextAnswerId;
    private String preAnswerId;
    private java.util.Date createTime;
    private String Date;

    public String getPreAnswerId() {
        return preAnswerId;
    }

    public void setPreAnswerId(String preAnswerId) {
        this.preAnswerId = preAnswerId;
    }

    public String getNextAnswerId() {
        return nextAnswerId;
    }

    public void setNextAnswerId(String nextAnswerId) {
        this.nextAnswerId = nextAnswerId;
    }

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

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public String getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(String likeNum) {
        this.likeNum = likeNum;
    }

    public String getAgreeNum() {
        return agreeNum;
    }

    public void setAgreeNum(String agreeNum) {
        this.agreeNum = agreeNum;
    }

    public String getCommentsNum() {
        return commentsNum;
    }

    public void setCommentsNum(String commentsNum) {
        this.commentsNum = commentsNum;
    }

    public java.util.Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(java.util.Date createTime) {
        this.createTime = createTime;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}
