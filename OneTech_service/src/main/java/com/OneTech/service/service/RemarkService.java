package com.OneTech.service.service;

import com.OneTech.common.service.IBaseService;
import com.OneTech.model.model.RemarkBean;
import com.alibaba.fastjson.JSONObject;

public interface RemarkService extends IBaseService<RemarkBean> {
    void updateRemakers(JSONObject requestJson)throws Exception;
    void updateTag(JSONObject requestJson)throws Exception;
    RemarkBean getRemakers(JSONObject requestJson)throws Exception;
}
