package com.OneTech.model.model;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "question_list")
public class QuestionListBean {
    @Id
    private String id;
    private String wechatId;
    private String title;
    private String explainText;
    private String languageType;
    private String followNum;
    private String viewNum;
    private String answerNum;
    private String pictureId;
    private String videoPath;
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

    public String getFollowNum() {
        return followNum;
    }

    public void setFollowNum(String followNum) {
        this.followNum = followNum;
    }

    public String getViewNum() {
        return viewNum;
    }

    public void setViewNum(String viewNum) {
        this.viewNum = viewNum;
    }

    public String getAnswerNum() {
        return answerNum;
    }

    public void setAnswerNum(String answerNum) {
        this.answerNum = answerNum;
    }

    public String getPictureId() {
        return pictureId;
    }

    public void setPictureId(String pictureId) {
        this.pictureId = pictureId;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
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
}
