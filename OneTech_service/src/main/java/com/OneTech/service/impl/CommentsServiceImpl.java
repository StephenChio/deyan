package com.OneTech.service.impl;

import com.OneTech.common.constants.CommentConstants;
import com.OneTech.common.service.impl.BaseServiceImpl;
import com.OneTech.common.util.UUIDUtils;
import com.OneTech.model.mapper.CommentsMapper;
import com.OneTech.model.model.CommentsBean;
import com.OneTech.model.model.UserInfoBean;
import com.OneTech.service.service.CommentsService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;

@Service("CommentsServiceImpl")
public class CommentsServiceImpl  extends BaseServiceImpl<CommentsBean> implements CommentsService {
    @Autowired
    CommentsMapper commentsMapper;
    @Override
    public void clickLike(JSONObject requestJson) throws Exception {
        CommentsBean commentsBean = new CommentsBean();
        commentsBean.setId(UUIDUtils.getRandom32());
        commentsBean.setMomentId(requestJson.getString("momentId"));
        commentsBean.setWechatId(requestJson.getString("wechatId"));
        commentsBean.setType(CommentConstants.LIKE);
        commentsBean.setCreateTime(new Date());
        this.save(commentsBean);
    }

    @Override
    public List<UserInfoBean> selectLike(JSONObject requestJson) throws Exception {
        return commentsMapper.selectLike(requestJson);
    }
}
