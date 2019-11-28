package com.OneTech.service.service;

import com.OneTech.common.service.IBaseService;
import com.OneTech.common.vo.StatusBean;
import com.OneTech.model.model.MomentsBean;
import com.alibaba.fastjson.JSONObject;

public interface MomentsService extends IBaseService<MomentsBean> {
    void publish(JSONObject requestJson)throws Exception;
}
