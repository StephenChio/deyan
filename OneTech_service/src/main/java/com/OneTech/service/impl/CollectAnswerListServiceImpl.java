package com.OneTech.service.impl;

import com.OneTech.common.constants.CollectConstants;
import com.OneTech.common.service.impl.BaseServiceImpl;
import com.OneTech.common.util.BooleanUtils;
import com.OneTech.common.util.UUIDUtils;
import com.OneTech.common.vo.QuestionListVO;
import com.OneTech.model.model.CollectAnswerListBean;
import com.OneTech.service.service.AnswerListService;
import com.OneTech.service.service.CollectAnswerListService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service("CollectAnswerListServiceImpl")
public class CollectAnswerListServiceImpl extends BaseServiceImpl<CollectAnswerListBean> implements CollectAnswerListService {
    @Autowired
    AnswerListService answerListService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void collectAnswer(JSONObject requestJson) throws Exception {
        CollectAnswerListBean collectAnswerListBean = new CollectAnswerListBean();

        collectAnswerListBean.setWechatId(requestJson.getString("wechatId"));
        collectAnswerListBean.setQuestionId(requestJson.getString("questionId"));
        collectAnswerListBean.setAnswerId(requestJson.getString("answerId"));

        collectAnswerListBean = this.selectOne(collectAnswerListBean);
        if(BooleanUtils.isEmpty(collectAnswerListBean)){
            collectAnswerListBean = new CollectAnswerListBean();
            collectAnswerListBean.setId(UUIDUtils.getRandom32());
            collectAnswerListBean.setWechatId(requestJson.getString("wechatId"));
            collectAnswerListBean.setQuestionId(requestJson.getString("questionId"));
            collectAnswerListBean.setAnswerId(requestJson.getString("answerId"));
            collectAnswerListBean.setCollectStatus(CollectConstants.COLLECTED);
            collectAnswerListBean.setCreateTime(new Date());
            this.save(collectAnswerListBean);
        }
        else{
            collectAnswerListBean.setCollectStatus(CollectConstants.COLLECTED);
            collectAnswerListBean.setUpdateTime(new Date());
            this.saveOrUpdate(collectAnswerListBean);
        }
        answerListService.updateCollectNum(collectAnswerListBean.getAnswerId(),1);

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void disCollectAnswer(JSONObject requestJson) throws Exception {
        CollectAnswerListBean collectAnswerListBean = new CollectAnswerListBean();
        collectAnswerListBean.setWechatId(requestJson.getString("wechatId"));
        collectAnswerListBean.setQuestionId(requestJson.getString("questionId"));
        collectAnswerListBean.setAnswerId(requestJson.getString("answerId"));
        collectAnswerListBean.setCollectStatus(CollectConstants.COLLECTED);
        collectAnswerListBean = this.selectOne(collectAnswerListBean);
        if(BooleanUtils.isNotEmpty(collectAnswerListBean)){
            collectAnswerListBean.setCollectStatus(CollectConstants.DIS_COLLECT);
            collectAnswerListBean.setUpdateTime(new Date());
            this.saveOrUpdate(collectAnswerListBean);
            answerListService.updateCollectNum(collectAnswerListBean.getAnswerId(),-1);
        }
    }

    @Override
    public boolean judgeIsCollect(JSONObject requestJson) throws Exception {
        CollectAnswerListBean collectAnswerListBean = new CollectAnswerListBean();
        collectAnswerListBean.setWechatId(requestJson.getString("wechatId"));
        collectAnswerListBean.setQuestionId(requestJson.getString("questionId"));
        collectAnswerListBean.setAnswerId(requestJson.getString("answerId"));
        collectAnswerListBean.setCollectStatus(CollectConstants.COLLECTED);
        return BooleanUtils.isNotEmpty(this.selectOne(collectAnswerListBean))?true:false;
    }
}
