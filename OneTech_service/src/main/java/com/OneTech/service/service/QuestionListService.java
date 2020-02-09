package com.OneTech.service.service;

import com.OneTech.common.service.IBaseService;
import com.OneTech.common.vo.QuestionListVO;
import com.OneTech.model.model.QuestionListBean;
import com.OneTech.model.model.UserLanguageBean;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

public interface QuestionListService extends IBaseService<QuestionListBean> {
    void questionPublish(JSONObject requestJson)throws Exception;
    List<QuestionListVO> getMyQuestion(JSONObject requestJson)throws Exception;
    List<QuestionListVO> getAllQuestionListByLanguageOption(JSONObject requestJson)throws Exception;
    List<QuestionListVO> getAllQuestionListByLanguage(UserLanguageBean userLanguageBean)throws Exception;
    List<QuestionListVO> getAllQuestionList()throws Exception;
    List<QuestionListVO> getQuestionDetail(List<QuestionListVO> questionListVOS)throws Exception;
}
