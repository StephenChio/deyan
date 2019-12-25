package com.OneTech.common.vo;

import java.util.List;
public class MomentsVO {
    private String wechatId;
    private String imgPath;
    private String userName;
    private String remarkName;
    private String text;
    private String id;
    private String videoPath;
    private String pictureId;
    private String backgroundImg;
    private List<?> commentsBeans;
    private List<?> likeName;
    private List<?> commentsContent;
    private List<String> pictureImgPath;
    private String createTime;

    public String getRemarkName() {
        return remarkName;
    }

    public void setRemarkName(String remarkName) {
        this.remarkName = remarkName;
    }

    public List<?> getLikeName() {
        return likeName;
    }

    public void setLikeName(List<?> likeName) {
        this.likeName = likeName;
    }

    public List<?> getCommentsContent() {
        return commentsContent;
    }

    public void setCommentsContent(List<?> commentsContent) {
        this.commentsContent = commentsContent;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<?> getCommentsBeans() {
        return commentsBeans;
    }

    public void setCommentsBeans(List<?> commentsBeans) {
        this.commentsBeans = commentsBeans;
    }

    public String getBackgroundImg() {
        return backgroundImg;
    }

    public void setBackgroundImg(String backgroundImg) {
        this.backgroundImg = backgroundImg;
    }

    public String getWechatId() {
        return wechatId;
    }

    public void setWechatId(String wechatId) {
        this.wechatId = wechatId;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    public String getPictureId() {
        return pictureId;
    }

    public void setPictureId(String pictureId) {
        this.pictureId = pictureId;
    }

    public List<String> getPictureImgPath() {
        return pictureImgPath;
    }

    public void setPictureImgPath(List<String> pictureImgPath) {
        this.pictureImgPath = pictureImgPath;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
