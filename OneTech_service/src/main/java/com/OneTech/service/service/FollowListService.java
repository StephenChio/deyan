package com.OneTech.service.service;

import com.OneTech.common.service.IBaseService;
import com.OneTech.model.model.FollowListBean;
import com.alibaba.fastjson.JSONObject;


public interface FollowListService extends IBaseService<FollowListBean> {
    boolean isFollowed(JSONObject requestJson)throws Exception;
    void disFollowQuestion(JSONObject requestJson)throws Exception;
    void followQuestion(JSONObject requestJson)throws Exception;
}
