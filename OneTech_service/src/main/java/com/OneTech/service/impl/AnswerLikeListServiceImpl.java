package com.OneTech.service.impl;

import com.OneTech.common.service.impl.BaseServiceImpl;
import com.OneTech.common.util.BooleanUtils;
import com.OneTech.common.util.UUIDUtils;
import com.OneTech.model.model.AnswerLikeListBean;
import com.OneTech.service.service.AnswerLikeListService;
import com.OneTech.service.service.AnswerListService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service("AnswerLikeListServiceImpl")
public class AnswerLikeListServiceImpl extends BaseServiceImpl<AnswerLikeListBean> implements AnswerLikeListService {
    @Autowired
    AnswerListService answerListService;
    @Override
    public void clickLike(JSONObject requestJson) throws Exception {
        AnswerLikeListBean answerLikeListBean = new AnswerLikeListBean();
        answerLikeListBean.setAnswerId(requestJson.getString("answerId"));
        answerLikeListBean.setWechatId(requestJson.getString("wechatId"));
        answerLikeListBean = this.selectOne(answerLikeListBean);
        if(BooleanUtils.isEmpty(answerLikeListBean)){
            answerLikeListBean = new AnswerLikeListBean();
            answerLikeListBean.setId(UUIDUtils.getRandom32());
            answerLikeListBean.setAnswerId(requestJson.getString("answerId"));
            answerLikeListBean.setWechatId(requestJson.getString("wechatId"));
            answerLikeListBean.setCreateTime(new Date());
            answerLikeListBean.setLikeStatus("LIKE");
            this.save(answerLikeListBean);
        }
        else{
            answerLikeListBean.setUpdateTime(new Date());
            answerLikeListBean.setLikeStatus("LIKE");
            this.saveOrUpdate(answerLikeListBean);
        }
        answerListService.updateLikeNum(answerLikeListBean.getAnswerId(),1);
    }

    @Override
    public void clickDisLike(JSONObject requestJson) throws Exception {
        AnswerLikeListBean answerLikeListBean = new AnswerLikeListBean();
        answerLikeListBean.setAnswerId(requestJson.getString("answerId"));
        answerLikeListBean.setWechatId(requestJson.getString("wechatId"));
        answerLikeListBean = this.selectOne(answerLikeListBean);
        answerLikeListBean.setLikeStatus("DISLIKE");
        answerLikeListBean.setUpdateTime(new Date());
        this.saveOrUpdate(answerLikeListBean);
        answerListService.updateLikeNum(answerLikeListBean.getAnswerId(),-1);
    }

    @Override
    public boolean judgeIsLike(JSONObject requestJson) throws Exception {
        AnswerLikeListBean answerLikeListBean = new AnswerLikeListBean();
        answerLikeListBean.setAnswerId(requestJson.getString("answerId"));
        answerLikeListBean.setWechatId(requestJson.getString("wechatId"));
        answerLikeListBean.setLikeStatus("LIKE");
        return BooleanUtils.isEmpty(this.selectOne(answerLikeListBean))?false:true;
    }
}