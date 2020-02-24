package com.OneTech.service.impl;

import com.OneTech.common.service.impl.BaseServiceImpl;
import com.OneTech.common.util.TimeUtils;
import com.OneTech.common.util.UUIDUtils;
import com.OneTech.common.vo.AnswerListVO;
import com.OneTech.model.mapper.AnswerListMapper;
import com.OneTech.model.model.AnswerListBean;
import com.OneTech.model.model.QuestionListBean;
import com.OneTech.service.service.AnswerListService;
import com.OneTech.service.service.QuestionListService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service("AnswerListServiceImpl")
public class AnswerListServiceImpl extends BaseServiceImpl<AnswerListBean> implements AnswerListService {
    @Autowired
    AnswerListMapper answerListMapper;
    @Autowired
    QuestionListService questionListService;

    @Override
    public void answerPublish(JSONObject requestJson) throws Exception {
        AnswerListBean answerListBean = new AnswerListBean();
        answerListBean.setId(UUIDUtils.getRandom32());
        answerListBean.setWechatId(requestJson.getString("wechatId"));
        answerListBean.setAnswerText(requestJson.getString("answerText"));
        answerListBean.setLikeNum("0");
        answerListBean.setCommentsNum("0");
        answerListBean.setCollectNum("0");
        answerListBean.setQuestionId(requestJson.getString("id"));
        answerListBean.setCreateTime(new Date());
        this.save(answerListBean);

        questionListService.updateAnswerNum(requestJson.getString("id"),1);
    }

    @Override
    public List<AnswerListVO> getAnswerListVOByQuestionId(String id) throws Exception {
        return answerListMapper.getAnswerListVOByQuestionId(id);
    }

    @Override
    public AnswerListVO getAnswerById(JSONObject requestJson) throws Exception {
        AnswerListVO answerListVO = answerListMapper.getAnswerById(requestJson);

        Date nowTime = new Date();

        answerListVO.setDate(TimeUtils.getLongDateAgo(nowTime.getTime() - answerListVO.getCreateTime().getTime()));

        answerListVO.setNextAnswerId(answerListMapper.getNextAnswerId(requestJson));
        answerListVO.setPreAnswerId(answerListMapper.getPreAnswerId(requestJson));

        return answerListVO;
    }

    @Override
    public void updateLikeNum(String id, int num) throws Exception {
        AnswerListBean answerListBean = new AnswerListBean();
        answerListBean.setId(id);
        answerListBean = this.selectOne(answerListBean);

        num = Integer.valueOf(answerListBean.getLikeNum()) + num;
        answerListBean.setLikeNum(String.valueOf(num));

        this.saveOrUpdate(answerListBean);
    }

    @Override
    public void updateCollectNum(String id, int num) throws Exception {
        AnswerListBean answerListBean = new AnswerListBean();
        answerListBean.setId(id);
        answerListBean = this.selectOne(answerListBean);

        num = Integer.valueOf(answerListBean.getCollectNum()) + num;
        answerListBean.setCollectNum(String.valueOf(num));

        this.saveOrUpdate(answerListBean);
    }

    @Override
    public void updateCommentsNum(String id, int num) throws Exception {
        AnswerListBean answerListBean = new AnswerListBean();
        answerListBean.setId(id);
        answerListBean = this.selectOne(answerListBean);

        num = Integer.valueOf(answerListBean.getCommentsNum()) + num;
        answerListBean.setCommentsNum(String.valueOf(num));

        this.saveOrUpdate(answerListBean);
    }
}
