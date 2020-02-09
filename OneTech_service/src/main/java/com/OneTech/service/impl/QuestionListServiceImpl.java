package com.OneTech.service.impl;

import com.OneTech.common.service.impl.BaseServiceImpl;
import com.OneTech.common.util.BooleanUtils;
import com.OneTech.common.util.TimeUtils;
import com.OneTech.common.util.UUIDUtils;
import com.OneTech.common.vo.QuestionListVO;
import com.OneTech.model.mapper.QuestionListMapper;
import com.OneTech.model.model.AnswerListBean;
import com.OneTech.model.model.QuestionListBean;
import com.OneTech.model.model.UserInfoBean;
import com.OneTech.model.model.UserLanguageBean;
import com.OneTech.service.service.AnswerListService;
import com.OneTech.service.service.QuestionListService;
import com.OneTech.service.service.UserInfoService;
import com.OneTech.service.service.UserLanguageService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("QuestionListServiceImpl")
public class QuestionListServiceImpl extends BaseServiceImpl<QuestionListBean> implements QuestionListService {
    @Autowired
    AnswerListService answerListService;
    @Autowired
    QuestionListMapper questionListMapper;
    @Autowired
    UserLanguageService userLanguageService;
    @Autowired
    UserInfoService userInfoService;

    @Override
    public void questionPublish(JSONObject requestJson) throws Exception {
        QuestionListBean questionListBean = new QuestionListBean();
        questionListBean.setId(UUIDUtils.getRandom32());
        questionListBean.setAnswerId(UUIDUtils.getRandom32());
        questionListBean.setWechatId(requestJson.getString("wechatId"));
        questionListBean.setTitle(requestJson.getString("title"));
        questionListBean.setExplainText(requestJson.getString("explainText"));
        questionListBean.setLanguageType(requestJson.getString("languageType"));
        questionListBean.setFollowNum("0");
        questionListBean.setCreateTime(new Date());
        this.save(questionListBean);
    }
    @Override
    public List<QuestionListVO> getMyQuestion(JSONObject requestJson) throws Exception {
        List<QuestionListVO> questionListVOS = questionListMapper.getMyQuestion(requestJson);
        return getQuestionDetail(questionListVOS);
    }

    @Override
    public List<QuestionListVO> getAllQuestionListByLanguageOption(JSONObject requestJson) throws Exception {
        UserLanguageBean userLanguageBean = new UserLanguageBean();
        userLanguageBean.setWechatId(requestJson.getString("wechatId"));
        userLanguageBean = userLanguageService.selectOne(userLanguageBean);
        if (BooleanUtils.isEmpty(userLanguageBean)) {
            return this.getAllQuestionList();
        } else {
            return this.getAllQuestionListByLanguage(userLanguageBean);
        }
    }

    @Override
    public List<QuestionListVO> getAllQuestionListByLanguage(UserLanguageBean userLanguageBean) throws Exception {
        JSONObject jsonObject = new JSONObject();
        List<String> list = new ArrayList<>();
        if (BooleanUtils.isNotEmpty(userLanguageBean.getMotherLanguage())) {
            list.add(userLanguageBean.getMotherLanguage());
        }
        if (BooleanUtils.isNotEmpty(userLanguageBean.getFirstLanguage())) {
            list.add(userLanguageBean.getFirstLanguage());
        }
        if (BooleanUtils.isNotEmpty(userLanguageBean.getSecondLanguage())) {
            list.add(userLanguageBean.getSecondLanguage());
        }
        if (BooleanUtils.isNotEmpty(userLanguageBean.getThirdLanguage())) {
            list.add(userLanguageBean.getThirdLanguage());
        }
        jsonObject.put("language", list);
        return getQuestionDetail(questionListMapper.getAllQuestionListByLanguage(jsonObject));
    }

    @Override
    public List<QuestionListVO> getAllQuestionList() throws Exception {
        return getQuestionDetail(questionListMapper.getAllQuestionList());
    }

    @Override
    public List<QuestionListVO> getQuestionDetail(List<QuestionListVO> questionListVOS) throws Exception {
        for (QuestionListVO questionListVO : questionListVOS) {
            UserInfoBean userInfoBean = new UserInfoBean();
            userInfoBean.setWechatId(questionListVO.getWechatId());
            userInfoBean = userInfoService.selectOne(userInfoBean);

            questionListVO.setUserName(userInfoBean.getUserName());
            questionListVO.setImgPath(userInfoBean.getImgPath());

            AnswerListBean answerListBean = new AnswerListBean();
            answerListBean.setQuestionId(questionListVO.getAnswerId());
            List<AnswerListBean> answerListBeanList = answerListService.select(answerListBean);

            questionListVO.setAnswerList(answerListBeanList);
            questionListVO.setAnswerNum(String.valueOf(answerListBeanList.size()));

            Date createTime = questionListVO.getCreateTime();
            Date nowTime = new Date();
            questionListVO.setDate(TimeUtils.getLongDateAgo(nowTime.getTime() - createTime.getTime()));
        }
        return questionListVOS;
    }
}
