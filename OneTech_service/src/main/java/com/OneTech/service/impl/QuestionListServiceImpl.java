package com.OneTech.service.impl;

import com.OneTech.common.service.impl.BaseServiceImpl;
import com.OneTech.common.util.BooleanUtils;
import com.OneTech.common.util.TimeUtils;
import com.OneTech.common.util.UUIDUtils;
import com.OneTech.common.vo.AnswerListVO;
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
    @Autowired
    QuestionListService questionListService;

    @Override
    public void questionPublish(JSONObject requestJson) throws Exception {
        QuestionListBean questionListBean = new QuestionListBean();
        questionListBean.setId(UUIDUtils.getRandom32());
        questionListBean.setWechatId(requestJson.getString("wechatId"));
        questionListBean.setTitle(requestJson.getString("title"));
        questionListBean.setExplainText(requestJson.getString("explainText"));
        questionListBean.setLanguageType(requestJson.getString("languageType"));
        questionListBean.setFollowNum("0");
        questionListBean.setAnswerNum("0");
        questionListBean.setViewNum("0");
        questionListBean.setCreateTime(new Date());
        this.save(questionListBean);
    }

    @Override
    public List<QuestionListVO> getMyQuestion(JSONObject requestJson) throws Exception {
        List<QuestionListVO> questionListVOS = questionListMapper.getMyQuestion(requestJson);
        return getQuestionDetail(questionListVOS);
    }

    @Override
    public List<QuestionListVO> getMyFollow(JSONObject requestJson) throws Exception {
        List<QuestionListVO> questionListVOS = questionListMapper.getMyFollow(requestJson);
        return getQuestionDetail(questionListVOS);
    }

    @Override
    public List<QuestionListVO> getMyAnswer(JSONObject requestJson) throws Exception {
        List<QuestionListVO> questionListVOS = questionListMapper.getMyAnswer(requestJson);
        return getQuestionDetail(questionListVOS);
    }

    @Override
    public List<QuestionListVO> getMyCollect(JSONObject requestJson) throws Exception {
        List<QuestionListVO> questionListVOS = questionListMapper.getMyCollect(requestJson);
        return getQuestionDetail(questionListVOS,"collect");
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
    public List<QuestionListVO> getQuestionDetail(List<QuestionListVO> questionListVOS, String answerId) throws Exception {
        for (QuestionListVO questionListVO : questionListVOS) {
            UserInfoBean userInfoBean = new UserInfoBean();
            userInfoBean.setWechatId(questionListVO.getWechatId());
            userInfoBean = userInfoService.selectOne(userInfoBean);

            questionListVO.setUserName(userInfoBean.getUserName());

            questionListVO.setImgPath(userInfoBean.getImgPath());

            Date createTime = questionListVO.getCreateTime();
            Date nowTime = new Date();
            questionListVO.setDate(TimeUtils.getLongDateAgo(nowTime.getTime() - createTime.getTime()));
        }
        return questionListVOS;
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
            answerListBean.setQuestionId(questionListVO.getId());

            List<AnswerListBean> answerListBeanList = answerListService.select(answerListBean);

            if (answerListBeanList.size() > 0 && BooleanUtils.isNotEmpty(answerListBeanList.get(0))) {
                questionListVO.setAnswer(answerListBeanList.get(0));
            }


            Date createTime = questionListVO.getCreateTime();
            Date nowTime = new Date();
            questionListVO.setDate(TimeUtils.getLongDateAgo(nowTime.getTime() - createTime.getTime()));
        }
        return questionListVOS;
    }

    @Override
    public QuestionListVO getQuestionDetail(QuestionListVO questionListVO) throws Exception {

        UserInfoBean userInfoBean = new UserInfoBean();
        userInfoBean.setWechatId(questionListVO.getWechatId());
        userInfoBean = userInfoService.selectOne(userInfoBean);

        questionListVO.setUserName(userInfoBean.getUserName());
        questionListVO.setImgPath(userInfoBean.getImgPath());

        List<AnswerListVO> answerListVOS = answerListService.getAnswerListVOByQuestionId(questionListVO.getId());

        Date nowTime = new Date();
        for(AnswerListVO answerListVO:answerListVOS){
            answerListVO.setDate(TimeUtils.getLongDateAgo(nowTime.getTime() - answerListVO.getCreateTime().getTime()));
        }

        questionListVO.setAnswer(answerListVOS);

        Date createTime = questionListVO.getCreateTime();
        questionListVO.setDate(TimeUtils.getLongDateAgo(nowTime.getTime() - createTime.getTime()));

        updateViewNum(questionListVO.getId(), 1);

        return questionListVO;
    }

    @Override
    public QuestionListVO getQuestionInformationById(JSONObject requestJson) throws Exception {
        return getQuestionDetail(questionListMapper.getQuestionInformationById(requestJson));
    }

    @Override
    public void updateViewNum(String id, int num) throws Exception {
        QuestionListBean questionListBean = new QuestionListBean();
        questionListBean.setId(id);
        questionListBean = this.selectOne(questionListBean);

        num = Integer.valueOf(questionListBean.getViewNum()) + num;
        questionListBean.setViewNum(String.valueOf(num));

        this.saveOrUpdate(questionListBean);

    }

    @Override
    public void updateFollowNum(String id, int num) throws Exception {
        QuestionListBean questionListBean = new QuestionListBean();
        questionListBean.setId(id);
        questionListBean = this.selectOne(questionListBean);

        num = Integer.valueOf(questionListBean.getFollowNum()) + num;
        questionListBean.setFollowNum(String.valueOf(num));
//
        this.saveOrUpdate(questionListBean);
    }

    @Override
    public void updateAnswerNum(String id, int num) throws Exception {
        QuestionListBean questionListBean = new QuestionListBean();
        questionListBean.setId(id);
        questionListBean = this.selectOne(questionListBean);

        num = Integer.valueOf(questionListBean.getAnswerNum()) + num;
        questionListBean.setAnswerNum(String.valueOf(num));

        this.saveOrUpdate(questionListBean);
    }
}
