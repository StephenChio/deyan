package com.OneTech.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import com.OneTech.common.service.impl.BaseServiceImpl;
import com.OneTech.common.constants.CommentConstants;
import com.OneTech.service.service.CommentsService;
import com.OneTech.model.mapper.CommentsMapper;
import org.springframework.stereotype.Service;
import com.OneTech.model.model.CommentsBean;
import com.OneTech.model.model.UserInfoBean;
import com.OneTech.common.util.UUIDUtils;
import com.alibaba.fastjson.JSONObject;
import java.util.Date;
import java.util.List;

@Service("CommentsServiceImpl")
public class CommentsServiceImpl extends BaseServiceImpl<CommentsBean> implements CommentsService {
    @Autowired
    CommentsMapper commentsMapper;

    /**
     * 点赞
     * @param requestJson
     * @throws Exception
     */
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

    /**
     * 获得某条朋友圈的点赞列表
     * @param requestJson
     * @return
     * @throws Exception
     */
    @Override
    public List<UserInfoBean> selectLike(JSONObject requestJson) throws Exception {
        return commentsMapper.selectLike(requestJson);
    }
}
