package com.OneTech.service.service;

import com.OneTech.common.service.IBaseService;
import com.OneTech.common.vo.QuestionListVO;
import com.OneTech.model.model.CollectAnswerListBean;
import com.alibaba.fastjson.JSONObject;

import java.util.List;


public interface CollectAnswerListService  extends IBaseService<CollectAnswerListBean> {
    void collectAnswer(JSONObject requestJson)throws Exception;
    void disCollectAnswer(JSONObject requestJson)throws Exception;
    boolean judgeIsCollect(JSONObject requestJson)throws Exception;
}
