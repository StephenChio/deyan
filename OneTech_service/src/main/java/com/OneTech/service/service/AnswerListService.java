package com.OneTech.service.service;

import com.OneTech.common.service.IBaseService;
import com.OneTech.common.vo.AnswerListVO;
import com.OneTech.model.model.AnswerListBean;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

public interface AnswerListService extends IBaseService<AnswerListBean> {
    void answerPublish(JSONObject requestJson)throws Exception;
    List<AnswerListVO> getAnswerListVOByQuestionId(String id)throws Exception;
    AnswerListVO getAnswerById(JSONObject requestJson)throws Exception;

    void updateLikeNum(String id, int num) throws Exception;

    void updateCollectNum(String id, int num) throws Exception;

    void updateCommentsNum(String id, int num) throws Exception;
}
