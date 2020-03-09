package com.OneTech.service.service;

import com.OneTech.common.service.IBaseService;
import com.OneTech.model.model.IdSaltBean;
import com.alibaba.fastjson.JSONObject;

public interface IdSaltService extends IBaseService<IdSaltBean> {
    String getSalt(JSONObject requestJson)throws Exception;
}
