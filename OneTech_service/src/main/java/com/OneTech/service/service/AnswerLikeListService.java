package com.OneTech.service.service;

import com.OneTech.common.service.IBaseService;
import com.OneTech.model.model.AnswerLikeListBean;
import com.alibaba.fastjson.JSONObject;

public interface AnswerLikeListService extends IBaseService<AnswerLikeListBean> {
    void clickLike(JSONObject requestJson) throws Exception;

    void clickDisLike(JSONObject requestJson) throws Exception;

    boolean judgeIsLike(JSONObject requestJson) throws Exception;
}
