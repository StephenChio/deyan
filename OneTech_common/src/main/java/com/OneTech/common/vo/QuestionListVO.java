package com.OneTech.common.vo;

import java.util.Date;
import java.util.List;

public class QuestionListVO<T> {
    private String id;
    private String wechatId;
    private String userName;
    private String imgPath;
    private String title;
    private String explainText;
    private String languageType;
    private String answerNum;
    private String viewNum;
    private T answer;
    private String followNum;
    private Date createTime;
    private String Date;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getExplainText() {
        return explainText;
    }

    public void setExplainText(String explainText) {
        this.explainText = explainText;
    }

    public String getLanguageType() {
        return languageType;
    }

    public void setLanguageType(String languageType) {
        this.languageType = languageType;
    }


    public String getAnswerNum() {
        return answerNum;
    }

    public void setAnswerNum(String answerNum) {
        this.answerNum = answerNum;
    }

    public String getViewNum() {
        return viewNum;
    }

    public void setViewNum(String viewNum) {
        this.viewNum = viewNum;
    }

    public T getAnswer() {
        return answer;
    }

    public void setAnswer(T answer) {
        this.answer = answer;
    }

    public String getFollowNum() {
        return followNum;
    }

    public void setFollowNum(String followNum) {
        this.followNum = followNum;
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
