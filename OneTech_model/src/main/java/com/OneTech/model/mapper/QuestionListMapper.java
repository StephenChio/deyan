package com.OneTech.model.mapper;

import com.OneTech.common.mapper.IBaseMapper;
import com.OneTech.common.vo.QuestionListVO;
import com.OneTech.model.model.QuestionListBean;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface QuestionListMapper extends IBaseMapper<QuestionListBean> {
    List<QuestionListVO> getMyQuestion(JSONObject requestJson);

    List<QuestionListVO> getMyFollow(JSONObject requestJson);

    List<QuestionListVO>getMyAnswer(JSONObject requestJson);

    List<QuestionListVO> getAllQuestionList();

    List<QuestionListVO> getAllQuestionListByLanguage(JSONObject requestJson);

    QuestionListVO getQuestionInformationById(JSONObject requestJson);
}
