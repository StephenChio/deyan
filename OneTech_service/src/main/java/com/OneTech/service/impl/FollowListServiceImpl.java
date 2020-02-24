package com.OneTech.service.impl;

import com.OneTech.common.constants.FollowConstants;
import com.OneTech.common.service.impl.BaseServiceImpl;
import com.OneTech.common.util.BooleanUtils;
import com.OneTech.common.util.UUIDUtils;
import com.OneTech.model.model.FollowListBean;
import com.OneTech.service.service.FollowListService;
import com.OneTech.service.service.QuestionListService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service("FollowListServiceImpl")
public class FollowListServiceImpl extends BaseServiceImpl<FollowListBean> implements FollowListService {
    @Autowired
    QuestionListService questionListService;

    @Override
    public boolean isFollowed(JSONObject requestJson) throws Exception {
        FollowListBean followListBean = new FollowListBean();
        followListBean.setWechatId(requestJson.getString("wechatId"));
        followListBean.setQuestionId(requestJson.getString("id"));
        followListBean.setFollowStatus(FollowConstants.FOLLOWED);
        if (BooleanUtils.isNotEmpty(this.selectOne(followListBean))) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void disFollowQuestion(JSONObject requestJson) throws Exception {
        FollowListBean followListBean = new FollowListBean();

        followListBean.setWechatId(requestJson.getString("wechatId"));
        followListBean.setQuestionId(requestJson.getString("id"));

        followListBean = this.selectOne(followListBean);

        followListBean.setFollowStatus(FollowConstants.DIS_FOLLOWED);
        this.saveOrUpdate(followListBean);

        questionListService.updateFollowNum(requestJson.getString("id"), -1);
    }

    @Override
    public void followQuestion(JSONObject requestJson) throws Exception {
        FollowListBean followListBean = new FollowListBean();

        followListBean.setWechatId(requestJson.getString("wechatId"));
        followListBean.setQuestionId(requestJson.getString("id"));

        followListBean = this.selectOne(followListBean);
        if (BooleanUtils.isEmpty(followListBean)) {
            followListBean = new FollowListBean();
            followListBean.setId(UUIDUtils.getRandom32());
            followListBean.setWechatId(requestJson.getString("wechatId"));
            followListBean.setQuestionId(requestJson.getString("id"));

            followListBean.setFollowStatus(FollowConstants.FOLLOWED);
            followListBean.setCreateTime(new Date());
            this.save(followListBean);
        } else {
            followListBean.setFollowStatus(FollowConstants.FOLLOWED);

            this.saveOrUpdate(followListBean);
        }
        questionListService.updateFollowNum(requestJson.getString("id"), 1);
    }
}
